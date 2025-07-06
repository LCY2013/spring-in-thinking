package org.fufeng.client;

import org.fufeng.mvc.model.StockPrice;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class ConfiguredWebClientCaller {

    public static void main(String[] args) throws InterruptedException {
        String baseUrl = "http://localhost:8088/";
        String apiPricePath = "/api/v2/price/{symbol}";

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                });
        //三种不同的创建方法
        //1
//        WebClient client = WebClient.create();
        //2
//        WebClient client = WebClient.create(baseUrl);
        //3
        WebClient client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrl)
                //设置默认请求头或者其他
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .filter((request, next)->
                        next.exchange(ClientRequest.from(request)
                                .header("x-b3-traceid","from tracer")
                                .build())
                )
                .build();
        client.method(HttpMethod.GET);
        WebClient.RequestHeadersUriSpec<?> uriSpec = client.get();
        WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri(
                        uriBuilder -> uriBuilder
                                //定义URI Path
//                        .path(apiPricesPath)
                                .path(apiPricePath)
                                //如果PATH中含有参数,在build中需要加入
                                .build("APPL")
                ).header(
                        "x-b3-traceid", "some id from context")
                .acceptCharset()
                .ifNoneMatch("*");
        //返回结果
        Mono<StockPrice> response = headersSpec.exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(StockPrice.class);
                    } else {
                        //使用flatMap来获取Exception，并且调用Mono.error(Throwable t)来抛出异常
                        return clientResponse.createException().flatMap(Mono::error);
                    }
                })
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)));
        response.subscribe(stockPrice -> System.out.println("this is the result " + stockPrice));
        Thread.sleep(10000L);
    }
}

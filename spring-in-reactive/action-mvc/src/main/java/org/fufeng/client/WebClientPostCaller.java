package org.fufeng.client;

import org.fufeng.mvc.model.Price;
import org.fufeng.mvc.model.Stock;
import org.fufeng.mvc.model.StockPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class WebClientPostCaller {

    public static void main(String[] args) throws InterruptedException {
        String baseUrl = "http://localhost:8088/";
        String apiPricePath = "/api/v2/price/{symbol}";
        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                //设置默认请求头或者其他
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
        WebClient.RequestBodyUriSpec uriSpec = client.post();
        LinkedMultiValueMap map = new LinkedMultiValueMap();
        map.add("k1", "v1");
        map.add("k2", "v2");
        WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri(
                        uriBuilder -> uriBuilder
                                //定义URI Path
//                        .path(apiPricesPath)
                                .path(apiPricePath)
                                //如果PATH中含有参数,在build中需要加入
                                .build("APPL"))
                .body(
//                        BodyInserters.fromMultipartData(map)
                        Mono.just(getMockStockPrice()), StockPrice.class);
        //                .header(
//                        "x-b3-traceid", "some id from context")
        //返回结果
        Mono<StockPrice> response = headersSpec.exchangeToMono(clientResponse -> {
            if (clientResponse.statusCode().equals(HttpStatus.OK)) {
                return clientResponse.bodyToMono(StockPrice.class);
            } else {
                //使用flatMap来获取Exception，并且调用Mono.error(Throwable t)来抛出异常
                return clientResponse.createException().flatMap(Mono::error);
            }
        });
        response.subscribe(stockPrice -> System.out.println("this is the result " + stockPrice));
        Thread.sleep(10000L);
    }

    public static StockPrice getMockStockPrice() {
        return StockPrice.builder()
                .stock(Stock.builder().symbol("APPLE").name("Apple Inc.").build())
                .price(Price.builder().coefficient(13079).exponent(-2).currency("USD").build())
                .build();
    }
}

//package org.fufeng.client;
//
//import org.fufeng.mvc.model.StockPrice;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;
//import org.springframework.web.client.AsyncRestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//public class AsyncApiCaller {
//    public static void main(String[] args) {
//        String baseUrl = "http://localhost:8088";
//        String apiPath = "/api/v2/price/{symbol}";
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setMaxPoolSize(10);
//        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(taskExecutor);
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
//        Map pathVarMap = new HashMap();
//        pathVarMap.put("symbol", "APPL");
//        URI uri = builder.path(apiPath)
//                .uriVariables(pathVarMap)
//                .build().encode().toUri();
////        ResponseEntity<String> result = asyncRestTemplate.getForEntity(uri, String.class);
//        ListenableFuture<ResponseEntity<StockPrice>> result = asyncRestTemplate.getForEntity(uri, StockPrice.class);
//        result.addCallback(new ListenableFutureCallback<ResponseEntity<StockPrice>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                log.error(ex.getMessage());
//            }
//
//            @Override
//            public void onSuccess(ResponseEntity<StockPrice> result) {
//                log.info(result.getBody().toString());
//            }
//        });
////        result.completable()
////                .thenAccept();
//        asyncRestTemplate.getRestOperations();
//    }
//}

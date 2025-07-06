package org.fufeng.client;

import org.fufeng.mvc.model.StockPrice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ApiCaller {
    public static void main(String[] args) {
        String baseUrl = "http://localhost:8088";
        String apiPath = "/api/v2/price/{symbol}";
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        Map pathVarMap = new HashMap();
        pathVarMap.put("symbol", "APPL");
        URI uri = builder.path(apiPath)
                .uriVariables(pathVarMap)
                .build().encode().toUri();
//        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        ResponseEntity<StockPrice> result = restTemplate.getForEntity(uri, StockPrice.class);
    }
}

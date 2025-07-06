package org.fufeng;

import org.fufeng.reactive.model.Stock;
import org.fufeng.reactive.model.StockSubscription;
import org.fufeng.reactive.model.StockSymbol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.nativex.hint.TypeAccess;
//import org.springframework.nativex.hint.TypeHint;

@SpringBootApplication
//@TypeHint(types = {Stock.class, StockSymbol.class, StockSubscription.class}, access = TypeAccess.DECLARED_FIELDS)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
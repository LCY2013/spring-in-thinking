//package org.fufeng.r2dbc;
//
//import org.fufeng.r2dbc.entity.StockDO;
//import org.fufeng.r2dbc.entity.StockSubscriptionDO;
//import io.r2dbc.spi.ConnectionFactory;
//import io.r2dbc.spi.Row;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.r2dbc.connection.R2dbcTransactionManager;
//import org.springframework.r2dbc.core.DatabaseClient;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.ReactiveTransactionManager;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.reactive.TransactionalOperator;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.function.Function;
//
//@Component
//@Slf4j
//public class R2dbcDemo implements CommandLineRunner {
//    @Autowired
//    private ConnectionFactory connectionFactory;
//
//    @Override
//    public void run(String... args) throws Exception {
//        DatabaseClient client = DatabaseClient.create(connectionFactory);
////        simpleSelect(client).subscribe(System.out::println);
//        findBySymbol("APPL", client).subscribe(System.out::println);
////        saveSymbol(new StockSubscriptionDO(null, "tester@qq.com", "NFLX"), client)
////                .subscribe(System.out::println);
//        ReactiveTransactionManager tm = new R2dbcTransactionManager(connectionFactory);
//        TransactionalOperator tx = TransactionalOperator.create(tm);
//        Mono<Void> txOperation = updateSymbol(client)
//                .then(
//                        saveSubscription(new StockSubscriptionDO(null, "tester@qq.com", "NFLX"), client)
//                                .then()
//                )
//                .as(tx::transactional);
//        txOperation.subscribe();
//
//        Mono<Void> partialTxOperation = updateSymbol(client)
//                .then(
//                        tx.transactional(saveSubscription(new StockSubscriptionDO(null, "tester@qq.com", "NFLX"), client)
//                                .then()
//                        )
//                );
//    }
//
//    @Transactional
//    private Mono<Integer> updateSymbol(DatabaseClient client) {
//        return client.sql("UPDATE stock set name=:name where symbol=:symbol")
//                .bind("symbol", "APPL")
//                .bind("name", "FOO")
//                .fetch()
//                .rowsUpdated();
//    }
//
//    private Mono<StockSubscriptionDO> saveSubscription(StockSubscriptionDO subscriptionDO, DatabaseClient client) {
//        return client.sql("INSERT INTO STOCK_SUBSCRIPTION (email, symbol) VALUES (:email, :symbol)")
//                .bind("email", subscriptionDO.getEmail())
//                .bind("symbol", subscriptionDO.getSymbol())
//                .filter(statement -> statement.returnGeneratedValues("id", "email", "symbol"))
//                .fetch()
//                .first()
//                .map(r ->
//                        new StockSubscriptionDO(
//                                (Long) r.get("id"),
//                                (String) r.get("email"),
//                                (String) r.get("symbol")
//                        )
//                );
//    }
//
//    private Flux<StockDO> findBySymbol(String symbol, DatabaseClient client) {
//        return client.sql("SELECT * FROM stock WHERE symbol like :symbol")
//                .bind("symbol", symbol)
//                .filter(statement -> statement.fetchSize(2))
//                .map(mapStockDORow())
//                .all();
//    }
//
//
//    private Mono<StockDO> simpleSelect(DatabaseClient client) {
//        Mono<StockDO> firstStock = client.sql("SELECT symbol, name FROM stock")
//                .map(mapStockDORow())
//                .first();
//        return firstStock;
//    }
//
//    @NotNull
//    private Function<Row, StockDO> mapStockDORow() {
//        return row -> new StockDO(
//                row.get("symbol", String.class),
//                row.get("name", String.class)
//        );
//    }
//
//
////    @Override
////    public void run(String... args) throws Exception {
////        R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);
////        runInsert(template)
////                .thenMany(runSelect(template))
////                .subscribe(System.out::println);
////        runUpdate(template);
////        runDelete(template);
////    }
//
////    private Mono<Integer> runDelete(R2dbcEntityTemplate template) {
////        return template.delete(StockDO.class)
////                .from("stock")
////                .matching(Query.query(Criteria.where("symbol").like("A%")))
////                .all();
////    }
////
////    private Mono<Integer> runUpdate(R2dbcEntityTemplate template) {
////        return template.update(StockDO.class)
////                .inTable("stock")
////                .matching(Query.query(Criteria.where("symbol").like("A%")))
////                .apply(Update.update("name", "foo"));
////    }
//
////    private Mono<StockDO> runInsert(R2dbcEntityTemplate template) {
////        return template.insert(StockDO.class)
////                .using(new StockDO("FB", "脸书"));
////    }
////
////    private Flux<StockDO> runSelect(R2dbcEntityTemplate template) {
////        return template.select(StockDO.class)
////                .from("stock")
////                .matching(Query.query(Criteria.where("symbol").like("A%")))
////                .all();
////    }
//
////    @Override
////    public void run(String... args) throws Exception {
////        ConnectionPoolConfiguration poolConfig = ConnectionPoolConfiguration.builder()
////                .connectionFactory(connectionFactory)
////                .maxSize(10)
////                .build();
////        ConnectionPool pool = new ConnectionPool(poolConfig);
////        Mono<Connection> connection = pool.create();
//////        Publisher<? extends Connection> connection = connectionFactory.create();
////        Mono.from(connection)
////                .flatMapMany(conn -> conn.createStatement(
////                        "select * from STOCK"
////                ).execute())
////                .concatMap(result -> result.map((row, metadata) ->
////                        new StockDO(
////                                String.valueOf(row.get("symbol")),
////                                String.valueOf(row.get("name"))
////                        ))
////                )
////                .subscribe(System.out::println);
////    }
//}

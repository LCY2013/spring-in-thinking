//package org.fufeng.project.repository;
//
//import org.fufeng.project.model.Coffee;
//import org.springframework.data.r2dbc.repository.query.Query;
//import org.springframework.data.repository.reactive.ReactiveCrudRepository;
//import reactor.core.publisher.Flux;
//
//public interface CoffeeRepository extends ReactiveCrudRepository<Coffee, Long> {
//    @Query("select * from t_coffee where name = $1")
//    Flux<Coffee> findByName(String name);
//}

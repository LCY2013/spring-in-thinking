package org.fufeng.reactive.controller;

import org.fufeng.reactive.model.StockSymbol;
import org.fufeng.reactive.service.RSubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Controller
@RequestMapping("/subscriptions")
@Slf4j
public class SubscriptionController {
    @Autowired
    private RSubscriptionService rSubscriptionService;

    @PostMapping
    public Mono<String> addSubscription(@ModelAttribute(value = "stockSymbol") StockSymbol symbol) {
        Mono<SecurityContext> securityContextMono = ReactiveSecurityContextHolder.getContext();
        return securityContextMono.map(sc -> sc.getAuthentication().getName())
                .publishOn(Schedulers.boundedElastic())
                .map(name -> {
                    rSubscriptionService.addSubscription(name, symbol.getSymbol()).log()
//                            .subscribeOn(Schedulers.newSingle("addSubThread"))
                            .subscribe();
                    return "redirect:/subscriptions?added=" + symbol.getSymbol();
                });
    }

    @GetMapping
    public Mono<String> subscription(Model model) {
        Mono<SecurityContext> securityContextMono = ReactiveSecurityContextHolder.getContext();
//        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        return securityContextMono.map(sc -> sc.getAuthentication().getName())
//                .subscribeOn(Schedulers.parallel())
                .publishOn(Schedulers.boundedElastic())
                .map(name -> {
                    model.addAttribute("email", name);
                    model.addAttribute("subscriptions",
                            new ReactiveDataDriverContextVariable(rSubscriptionService.findByEmail(name)));
                    return "subscription";
                });
    }

    @ExceptionHandler({ResponseStatusException.class})
    public String exception(ResponseStatusException e) {
        log.error("This is an responseStatusException");
        return "error/5xx";
    }

}
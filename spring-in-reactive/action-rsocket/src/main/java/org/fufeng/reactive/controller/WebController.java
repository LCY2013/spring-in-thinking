package org.fufeng.reactive.controller;

import org.fufeng.reactive.service.RStockPriceService;
import org.fufeng.reactive.service.RSubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Controller
@Slf4j
public class WebController {

    @Autowired
    private RSubscriptionService rSubscriptionService;

    @Autowired
    private RStockPriceService rStockPriceService;

    @RequestMapping("/")
    public Mono<String> index(Model model) {
        log.info("index method invoked");
        Mono<SecurityContext> securityContextMono = ReactiveSecurityContextHolder.getContext();
       return  securityContextMono.map(sc -> sc.getAuthentication().getName())
                .subscribeOn(Schedulers.parallel())
                .publishOn(Schedulers.boundedElastic())
                .map(name -> {
                    model.addAttribute("email", name);
                    model.addAttribute("subscriptions",
                            new ReactiveDataDriverContextVariable(rSubscriptionService.findByEmail(name)));
                    return "index";
                });
    }

    @RequestMapping("/login")
    public String login() {
        log.info("login method invoked");
        return "login";
    }
}

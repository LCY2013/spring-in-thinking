package org.fufeng.async.controller;

import org.fufeng.async.service.RSubscriptionService;
import org.fufeng.mvc.model.StockSymbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    private RSubscriptionService rSubscriptionService;

    @PostMapping
    public DeferredResult<String> addSubscription(@ModelAttribute(value = "stockSymbol") StockSymbol symbol) {
        DeferredResult<String> result = new DeferredResult<>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                rSubscriptionService.addSubscriptionAsync(name, symbol.getSymbol()).get(5, TimeUnit.SECONDS);
                result.setResult("redirect:/subscriptions?added=" + symbol.getSymbol());
            } catch (Exception e) {
                result.setErrorResult(e);
            }
        });
        return result;
    }

    @GetMapping
    public CompletableFuture<String> subscription(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        CompletableFuture<String> getSubscriptionModelFuture = rSubscriptionService.findByEmail(name)
                .thenApplyAsync((subscriptions) -> {
                    model.addAttribute("email", name);
                    model.addAttribute("subscriptions", subscriptions);
                    return "subscription";
                });
        return getSubscriptionModelFuture;
    }


}

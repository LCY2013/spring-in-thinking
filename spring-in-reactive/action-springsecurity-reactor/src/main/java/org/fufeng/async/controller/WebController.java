package org.fufeng.async.controller;

import org.fufeng.async.service.RStockPriceService;
import org.fufeng.async.service.RSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Controller
public class WebController {

    @Autowired
    private RSubscriptionService rSubscriptionService;

    @Autowired
    private RStockPriceService rStockPriceService;

    @RequestMapping("/")
    public DeferredResult<String> index(Model model) {
        DeferredResult<String> result = new DeferredResult<>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        ForkJoinPool.commonPool().submit(() -> {
            try {
                model.addAttribute("email", name);
                model.addAttribute("stockPrices", rStockPriceService.getPriceAsync(name).get(5, TimeUnit.SECONDS));
                result.setResult("index");
            } catch (Exception e) {
                result.setErrorResult(e);
            }
        });
        return result;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}

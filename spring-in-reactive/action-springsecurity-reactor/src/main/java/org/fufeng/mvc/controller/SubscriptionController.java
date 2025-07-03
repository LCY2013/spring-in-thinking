package org.fufeng.mvc.controller;

import org.fufeng.mvc.model.StockSubscription;
import org.fufeng.mvc.model.StockSymbol;
import org.fufeng.mvc.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public String addSubscription(@ModelAttribute(value = "stockSymbol") StockSymbol symbol) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        subscriptionService.addSubscription(name, symbol.getSymbol());
        return "redirect:/subscriptions?added=" + symbol.getSymbol();
    }

    @GetMapping
    public String subscription(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<StockSubscription> subscriptions = subscriptionService.findByEmail(name);
        model.addAttribute("email", name);
        model.addAttribute("subscriptions", subscriptions);

        return "subscription";
    }


}

package org.fufeng.servlet.mvc.controller;

import org.fufeng.servlet.mvc.service.StockPriceService;
import org.fufeng.servlet.mvc.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.fufeng.servlet.mvc.Constants.TEST_USER_EMAIL;

@Controller
public class WebController {

    @Autowired
    private SubscriptionService subscriptionServiceImpl;

    @Autowired
    private StockPriceService stockPriceService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("email", TEST_USER_EMAIL);
        model.addAttribute("stockPrices", stockPriceService.getPrice(TEST_USER_EMAIL));
        return "index";
    }
}

package org.fufeng.async.controller;

import org.fufeng.async.service.RUserService;
import org.fufeng.mvc.dto.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private RUserService rUserService;

    @ModelAttribute("user")
    public UserRegistration userRegistrationDto() {
        return new UserRegistration();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") UserRegistration userRegistration) throws ExecutionException, InterruptedException {
        rUserService.saveAsync(userRegistration).get();
        return "redirect:/registration?success";
    }
}

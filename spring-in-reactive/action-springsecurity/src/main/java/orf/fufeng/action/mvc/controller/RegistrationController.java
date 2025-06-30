package orf.fufeng.action.mvc.controller;

import orf.fufeng.action.mvc.dto.UserRegistration;
import orf.fufeng.action.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistration userRegistrationDto() {
        return new UserRegistration();
    }

    @GetMapping
    public String showRegistrationForm(){
        return "registration";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") UserRegistration userRegistration){
        userService.save(userRegistration);
        return "redirect:/registration?success";
    }
}

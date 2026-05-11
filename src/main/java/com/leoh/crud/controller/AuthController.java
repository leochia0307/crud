package com.leoh.crud.controller;

import com.leoh.crud.model.User;
import com.leoh.crud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 登入頁面
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 註冊頁面
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // 註冊提交
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("errorMessage", "此帳號已被註冊！");
            return "register";
        }

        userService.registerNewUser(user);
        return "redirect:/login?success";
    }
}
package com.court.booking.controller;

import com.court.booking.Model.DTO.LoginRequest;
import com.court.booking.Model.DTO.RegisterRequest;
import com.court.booking.Model.Role;
import com.court.booking.Model.User;
import com.court.booking.Model.UserDetailsCustom;
import com.court.booking.service.AuthService;
import com.court.booking.service.Impl.UserDetailsServiceImpl;
import com.court.booking.service.UserService;
import com.court.booking.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("request", new RegisterRequest());
        return "public/register.html";
    }
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("login", new LoginRequest());
        return "public/login.html";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") @Valid LoginRequest request
            , BindingResult bind, HttpServletResponse response, Model model) {
        if(bind.hasErrors()){
            return "public/login.html";
        }
        boolean isSuccess = authService.login(request, response);

        if(!isSuccess){
            model.addAttribute("loginFailed", "Tài khoản hoặc mật khẩu không chính xác");
            return "public/login.html";
        }
        return "redirect:/dashboard";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute("request") @Valid RegisterRequest request, BindingResult bind) {

        if(bind.hasErrors()){
            return "public/register.html";
        }

        boolean isSuccess = authService.register(request);

        if(isSuccess){
            return "redirect:/auth/login";
        }

        return "public/register.html";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        authService.logout(request, response);
        return "redirect:/auth/login";
    }
}
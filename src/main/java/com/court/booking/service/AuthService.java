package com.court.booking.service;


import com.court.booking.Model.DTO.LoginRequest;
import com.court.booking.Model.DTO.RegisterRequest;
import com.court.booking.Model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    boolean login(LoginRequest request, HttpServletResponse response);

    boolean register(RegisterRequest request);

    void logout(HttpServletRequest request, HttpServletResponse response);
}

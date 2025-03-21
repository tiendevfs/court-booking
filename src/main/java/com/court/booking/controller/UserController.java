package com.court.booking.controller;

import com.court.booking.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;



}

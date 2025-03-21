package com.court.booking.controller.dashboard;

import com.court.booking.Model.User;
import com.court.booking.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
@AllArgsConstructor
public class DashboardController {
    @GetMapping
    String getDashboard(){
        return "private/index.html";
    }


}

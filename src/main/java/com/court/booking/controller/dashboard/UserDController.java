package com.court.booking.controller.dashboard;

import com.court.booking.Model.DTO.UserRequest;
import com.court.booking.Model.User;
import com.court.booking.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
@AllArgsConstructor
public class UserDController {
    private final UserService userService;

    @GetMapping("/user")
    String getUsers(Model model, @RequestParam("page") Optional<String> page){


        Page<User> users = userService.findAll(page);
        model.addAttribute("users", users);
        model.addAttribute("total", users.getTotalPages());
        model.addAttribute("current", page.map(Integer::parseInt).orElse(1));
        return "private/user/user.html";
    }
    @GetMapping("/user/{id}")
    @ResponseBody
    ResponseEntity<User> getUser(Model model, @PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/user/post")
    String getPageCreate(Model model){
        model.addAttribute("request",new UserRequest());
        return "private/user/post.html";
    }

    @GetMapping("/user/put")
    String getPageUpdate(Model model){
        model.addAttribute("request",new UserRequest());
        return "private/user/put.html";
    }
    @PostMapping("/user")
    String postUser(@ModelAttribute("request") @Valid UserRequest request, BindingResult bind, Model model){
        if(bind.hasErrors()){
            return "private/user/post.html";
        }

        boolean isCreated = userService.create(request);
        if(!isCreated){
            model.addAttribute("failed", "Tài khoản đã tồn tại");
            return "private/user/post.html";
        }
        return "redirect:/dashboard/user";
    }
    @PutMapping("/user/{id}")
    void putUser(@PathVariable Long id){

    }

    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Long id){

    }

}

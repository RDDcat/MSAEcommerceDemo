package com.maro.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UsersController {

    @GetMapping("/health_check")
    public String status(){
        return "It's working";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "It's working";
    }


}
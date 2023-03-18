package com.maro.userservice.controller;

import com.maro.userservice.vo.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UsersController {
    private Environment env;

    @Autowired
    private Greeting greeting;

    @Autowired
    public UsersController(Environment env){
        this.env= env;
    }

    @GetMapping("/health_check")
    public String status(){
        return "It's working";
    }

    @GetMapping("/welcome")
    public String welcome(){
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }


}

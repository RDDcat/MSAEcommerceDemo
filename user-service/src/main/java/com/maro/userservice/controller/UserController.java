package com.maro.userservice.controller;

import com.maro.userservice.dto.UserDTO;
import com.maro.userservice.service.UserService;
import com.maro.userservice.vo.Greeting;
import com.maro.userservice.vo.RequestUser;
import com.maro.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service")
public class UserController {
    private Environment env;
    private UserService userService;

    @Autowired
    private Greeting greeting;

    @Autowired
    public UserController(Environment env,UserService userService){
        this.env= env;
        this.userService = userService;
    }

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's working on port %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome(){
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO userDTO = mapper.map(user, UserDTO.class);
        userService.createUser(userDTO);

        ResponseUser responseUser = mapper.map(userDTO, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

}

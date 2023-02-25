package com.robertjuhas.rest;

import com.robertjuhas.dto.SubscribeRequest;
import com.robertjuhas.dto.UserRequest;
import com.robertjuhas.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @PostMapping
    public Object createUser(@RequestBody UserRequest userRequest) {
        userService.save(userRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/subscribe")
    public Object subscribe(@RequestBody SubscribeRequest subscribe) {
        userService.subscribe(subscribe);
        return ResponseEntity.ok().build();
    }
}

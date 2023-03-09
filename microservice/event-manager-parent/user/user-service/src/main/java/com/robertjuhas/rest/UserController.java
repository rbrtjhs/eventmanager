package com.robertjuhas.rest;

import com.robertjuhas.dto.request.SubscribeRequestDTO;
import com.robertjuhas.dto.response.UserResponseDTO;
import com.robertjuhas.dto.request.CreateUserRequestDTO;
import com.robertjuhas.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @PostMapping
    public Object createUser(@RequestBody CreateUserRequestDTO userRequest) {
        userService.save(userRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/subscribe")
    public Object subscribe(@RequestBody SubscribeRequestDTO subscribe) {
        userService.subscribe(subscribe);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{id}")
    public UserResponseDTO getUser(@PathVariable("id") long id) {
        return userService.getUser(id);
    }
}

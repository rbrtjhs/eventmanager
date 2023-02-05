package com.robertjuhas.service;

import com.robertjuhas.dto.UserRequest;
import com.robertjuhas.entity.User;
import com.robertjuhas.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record UserService(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {

    public void save(UserRequest userRequest) {
        var user = new User();
        user.setUsername(userRequest.getUsername());
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}

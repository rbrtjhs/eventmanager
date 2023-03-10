package com.robertjuhas.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "user")
public class UserService {
    private String url;
}

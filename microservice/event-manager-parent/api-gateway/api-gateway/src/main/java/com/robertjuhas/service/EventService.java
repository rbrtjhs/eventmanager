package com.robertjuhas.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "event")
public class EventService {

    private final Command command = new Command();
    private final Query query = new Query();

    @Data
    public static class Command {
        private String url;
    }

    @Data
    public static class Query {
        private String url;
    }
}

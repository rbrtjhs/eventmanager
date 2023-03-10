package com.robertjuhas;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;


@EnableKafka
@SpringBootApplication
public class EventQueryApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventQueryApplication.class, args);
    }
}

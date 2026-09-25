package com.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerHealthMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerHealthMonitorApplication.class, args);
    }
}

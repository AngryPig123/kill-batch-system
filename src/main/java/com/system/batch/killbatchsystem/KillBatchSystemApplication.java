package com.system.batch.killbatchsystem;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KillBatchSystemApplication {
    public static void main(String[] args) {
        //  System.exit(SpringApplication.exit(SpringApplication.run(KillBatchSystemApplication.class, args)));
        SpringApplication.run(KillBatchSystemApplication.class, args);
    }
}

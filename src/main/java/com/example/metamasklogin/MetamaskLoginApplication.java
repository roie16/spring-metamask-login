package com.example.metamasklogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MetamaskLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetamaskLoginApplication.class, args);
    }

}

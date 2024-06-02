package com.example.metamasklogin;

import com.example.metamasklogin.testcontainers.ContainersConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MetamaskLoginApplicationTest {

    public static void main(String[] args) {
        SpringApplication
                .from(MetamaskLoginApplication::main)
                .with(ContainersConfig.class)
                .run(args);
    }

}

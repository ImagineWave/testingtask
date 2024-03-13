package ru.strid.testingtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class TestingtaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestingtaskApplication.class, args);
    }

}

package com.globallogic.globallogic;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

//@PropertySource("file:${APP_ENV}")
@SpringBootApplication
public class GlobalLogicApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobalLogicApplication.class, args);
    }

}

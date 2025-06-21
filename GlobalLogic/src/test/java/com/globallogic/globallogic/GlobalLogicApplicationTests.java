package com.globallogic.globallogic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mockStatic;


@SpringBootTest
class GlobalLogicApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    public void ejecucion_exitosa() {
        String[] args = {};

        try {
            GlobalLogicApplication.main(args);
        } catch (Exception e) {
            fail("Application failed to start with default configuration");
        }
    }

}

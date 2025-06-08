package com.globallogic.globallogic.controller;

import com.globallogic.globallogic.controller.request.SignUpRequest;
import com.globallogic.globallogic.controller.response.LoginResponse;
import com.globallogic.globallogic.controller.response.SignUpResponse;
import com.globallogic.globallogic.service.IUsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class JavaEvaluationControllerImplTest {

    private UsersControllerImpl controller;
    private IUsersService service;

    @BeforeEach
    void setUp() {
        service = mock(IUsersService.class);
        controller = new UsersControllerImpl(service);
    }

    @Test
    void signUp_Http201() {
        SignUpRequest request = new SignUpRequest();
        SignUpResponse responseMock = new SignUpResponse();
        when(service.signUp(request)).thenReturn(responseMock);

        ResponseEntity<SignUpResponse> response = controller.signUp(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response);
    }

    @Test
    void login_Http200() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("jwt")).thenReturn("token");

        ResponseEntity<LoginResponse> response = controller.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }
}


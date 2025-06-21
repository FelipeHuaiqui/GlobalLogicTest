package com.globallogic.globallogic.controller;

import com.globallogic.globallogic.dto.request.SignUpRequest;
import com.globallogic.globallogic.dto.response.LoginResponse;
import com.globallogic.globallogic.dto.response.SignUpResponse;
import com.globallogic.globallogic.service.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController()
@RequestMapping("/api-v1")
@AllArgsConstructor
public class UsersController {

    @Autowired
    private IUsersService javaEvaluationService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest user) {
        SignUpResponse response = javaEvaluationService.signUp(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(HttpServletRequest request) {
        String token = String.valueOf(request.getAttribute("jwt"));
        LoginResponse response = javaEvaluationService.logIn(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

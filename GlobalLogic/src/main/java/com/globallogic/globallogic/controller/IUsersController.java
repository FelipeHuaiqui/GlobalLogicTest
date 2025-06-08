package com.globallogic.globallogic.controller;

import com.globallogic.globallogic.controller.request.SignUpRequest;
import com.globallogic.globallogic.controller.response.LoginResponse;
import com.globallogic.globallogic.controller.response.SignUpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public interface IUsersController {
    ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest user);
    ResponseEntity<LoginResponse> login(HttpServletRequest request);
}

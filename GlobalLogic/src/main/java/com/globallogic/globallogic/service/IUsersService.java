package com.globallogic.globallogic.service;

import com.globallogic.globallogic.controller.request.SignUpRequest;
import com.globallogic.globallogic.controller.response.LoginResponse;
import com.globallogic.globallogic.controller.response.SignUpResponse;

public interface IUsersService {
    SignUpResponse signUp(SignUpRequest signUpRequest);
    LoginResponse logIn(String token);

}

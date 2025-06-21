package com.globallogic.globallogic.service;

import com.globallogic.globallogic.dto.request.SignUpRequest;
import com.globallogic.globallogic.dto.response.LoginResponse;
import com.globallogic.globallogic.dto.response.SignUpResponse;

public interface IUsersService {
    SignUpResponse signUp(SignUpRequest signUpRequest);
    LoginResponse logIn(String token);

}

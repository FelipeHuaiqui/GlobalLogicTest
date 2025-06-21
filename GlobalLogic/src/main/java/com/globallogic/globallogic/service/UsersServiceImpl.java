package com.globallogic.globallogic.service;

import com.globallogic.globallogic.dto.request.PhonesTO;
import com.globallogic.globallogic.dto.request.SignUpRequest;
import com.globallogic.globallogic.dto.response.LoginResponse;
import com.globallogic.globallogic.dto.response.SignUpResponse;
import com.globallogic.globallogic.exception.ConflictException;
import com.globallogic.globallogic.exception.NotFound;
import com.globallogic.globallogic.repository.model.Phone;
import com.globallogic.globallogic.repository.model.UserRegister;
import com.globallogic.globallogic.repository.UserRepository;
import com.globallogic.globallogic.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UsersServiceImpl implements IUsersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        try {
            Optional<UserRegister> userSearch = userRepository.findByEmail(signUpRequest.getEmail());
            if (userSearch.isPresent()) {
                throw new ConflictException("El usuario ya existe para el email " + userSearch.get().getEmail());
            }
            String token = JwtUtil.generarToken(signUpRequest.getEmail());
            UserRegister savedUser = persistUser(signUpRequest, token);
            return buildResponseSign(savedUser);
        }catch (Exception e){
            log.info("Error en sign-up: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public LoginResponse logIn(String token) {
        UserRegister user = new UserRegister();
        try{
            Optional<UserRegister> optionalUser = userRepository.findByToken(token);
            if (optionalUser.isPresent()) {
                user =  optionalUser.get();
            } else {
                throw new NotFound("No hay usuario para el token");
            }
            String nuevoToken = JwtUtil.generarToken(user.getEmail());
            user.setToken(nuevoToken);
            user.setLastLogin(formatDate(LocalDateTime.now()));
            userRepository.save(user);
            LoginResponse loginResponse = buildResponseLogin(user);
            return loginResponse;
        }catch (Exception e){
            log.info("Error en login: {}", e.getMessage());
            throw e;
        }
    }

    private UserRegister persistUser(SignUpRequest signUpRequest, String token) {

        UserRegister userRegister = new UserRegister();
        userRegister.setId(UUID.randomUUID());
        userRegister.setName(signUpRequest.getName());
        userRegister.setEmail(signUpRequest.getEmail());
        userRegister.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRegister.setToken(token);
        userRegister.setCreated(formatDate(LocalDateTime.now()));
        userRegister.setLastLogin(formatDate(LocalDateTime.now()));
        userRegister.setIsActive(true);

        List<Phone> phones = signUpRequest.getPhones().stream()
                .map(p -> new Phone(p.getNumber(), p.getCitycode(), p.getCountrycode()))
                .collect(Collectors.toList());
        userRegister.setPhones(phones);

        return userRepository.save(userRegister);
    }


    private SignUpResponse buildResponseSign(UserRegister user) {
        SignUpResponse response = new SignUpResponse();
        response.setId(user.getId());
        response.setCreated(user.getCreated().toString());
        response.setLastLogin(user.getLastLogin().toString());
        response.setToken(user.getToken());
        response.setIsActive(user.getIsActive());
        return response;
    }

    private LoginResponse buildResponseLogin(UserRegister user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(String.valueOf(user.getId()));
        loginResponse.setCreated(user.getCreated());
        loginResponse.setLastLogin(formatDate(LocalDateTime.now()));
        loginResponse.setToken(user.getToken());
        loginResponse.setIsActive(user.getIsActive());
        loginResponse.setName(user.getName());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setPassword(user.getPassword());
        List<PhonesTO> phones = user.getPhones()
                .stream()
                .map(p -> new PhonesTO(p.getNumber(), p.getCitycode(), p.getCountrycode()))
                .collect(Collectors.toList());
        loginResponse.setPhones(phones);
        return loginResponse;
    }

    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss a");
        String fechaFormateada = date.format(formato);
        return fechaFormateada;
    }
}

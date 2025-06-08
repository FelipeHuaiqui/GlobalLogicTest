package com.globallogic.globallogic.service;

import com.globallogic.globallogic.controller.request.PhonesTO;
import com.globallogic.globallogic.controller.request.SignUpRequest;
import com.globallogic.globallogic.controller.response.LoginResponse;
import com.globallogic.globallogic.controller.response.SignUpResponse;
import com.globallogic.globallogic.exception.ConflictException;
import com.globallogic.globallogic.exception.NotFound;
import com.globallogic.globallogic.repository.Phone;
import com.globallogic.globallogic.repository.UserRegister;
import com.globallogic.globallogic.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JavaEvaluationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersServiceImpl service;

    private static SignUpRequest request;

    private static UserRegister user;

    @BeforeAll
    static void setUp() {
        //para sign-up
        request = new SignUpRequest();
        request.setName("Felipe");
        request.setEmail("felipe@test.com");
        request.setPassword("Test1234");
        request.setPhones(Collections.singletonList(new PhonesTO(569364181,1322,"CL")));
        //para login
        user = new UserRegister();
        user.setId(UUID.randomUUID());
        user.setEmail("felipe@test.com");
        user.setName("Felipe");
        user.setPassword("Test1234");
        user.setToken("token");
        user.setPhones(Arrays.asList(new Phone(569364181,1322,"CL")));
    }

    @Test
    void signUp_UsuarioNoExiste_Guarda_GeneraResponse() {
        /*
        usuario no existente para el find
        persistUser (contiene el encoder del password y que guarde)
        ejecuta el metodo, aborda los anteriores + 3.El caso de generar un response
        valido que la respuesta fue contruida
        */
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("claveEncriptada");
        when(userRepository.save(any(UserRegister.class))).thenAnswer(i -> i.getArgument(0));

        SignUpResponse response = service.signUp(request);

        assertNotNull(response);
    }

    @Test
    void signUp_UsuarioExiste_ConflictException() {
         /*
         caso de que usuario si existe
         lanza Excepcion conflicto
         verificar que no se intento guardar
         */
        when(userRepository.findByEmail("felipe@test.com")).thenReturn(Optional.of(user));

        assertThrows(ConflictException.class, () -> service.signUp(request));

        verify(userRepository, never()).save(any());
    }

    @Test
    void logIn_UsuarioExiste_GeneraNuevoToken_Guarda_GeneraResponse() {
         /*
         encuentra usuario en el find
         guarda el usuario con el nuevo LastLogin y el nuevo token
         ejecutar el 1, el 2 y 3.generaria response
         */

        when(userRepository.findByToken("token")).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserRegister.class))).thenAnswer(i -> i.getArgument(0));

        LoginResponse response = service.logIn("token");

        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getName(), response.getName());
        assertNotNull(response.getToken());
        verify(userRepository).save(user);
    }

    @Test
    void logIn_UsuarioNoExiste_NotFoundExcepcion() {
        /*Se hace login con token no asociado a un usuario
        cae en un not found, verifico que se lance la excepcion*/
        when(userRepository.findByToken("token")).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> service.logIn("token"));

        verify(userRepository, never()).save(any());
    }
}
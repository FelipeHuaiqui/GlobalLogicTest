package com.globallogic.globallogic.exception;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlerControllerTest {

    private ExceptionHandlerController controller;

    @BeforeEach
    void setUp() {
        controller = new ExceptionHandlerController();
    }

    @Test
    public void testHandleValidationErrors() {
        FieldError fieldError = new FieldError("objeto", "email", "no puede estar vacío");

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ExceptionModel> response = controller.handleValidationErrors(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ExceptionModel body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.getError().size());
        assertTrue(body.getError().get(0).getDetail().contains("email"));
    }

    @Test
    void handleUnknownField() {
        // Crear UnrecognizedPropertyException usando método estático
        UnrecognizedPropertyException ex = UnrecognizedPropertyException.from(
                Mockito.mock(JsonParser.class),
                Object.class,
                "campo_invalido",
                Collections.emptyList()
        );

        ResponseEntity<ExceptionModel> response = controller.handleUnknownField(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ExceptionModel body = response.getBody();
        assertNotNull(body);
        assertEquals("Campo no reconocido: 'campo_invalido'", body.getError().get(0).getDetail());
    }

    @Test
    public void testConflictException() {
        Exception ex = new ConflictException("Conflicto de datos");
        ResponseEntity<ExceptionModel> response = controller.ConflictException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        ExceptionModel body = response.getBody();
        assertNotNull(body);
        assertEquals("Conflicto de datos", body.getError().get(0).getDetail());
        assertEquals(HttpStatus.CONFLICT.value(), body.getError().get(0).getCodigo());
    }
    @Test
    public void testBadRequestException() {
        Exception ex = new HttpClientErrorException(
                HttpStatus.BAD_REQUEST,
                "Solicitud incorrecta",
                HttpHeaders.EMPTY,
                "Solicitud incorrecta".getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8
        );

        ResponseEntity<ExceptionModel> response = controller.BadRequestException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ExceptionModel body = response.getBody();
        assertNotNull(body);
        assertEquals("400 Solicitud incorrecta", body.getError().get(0).getDetail());
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getError().get(0).getCodigo());
    }

    @Test
    public void testInternalServerErrorException() {
        Exception ex = new HttpServerErrorException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno",
                HttpHeaders.EMPTY,
                "Error interno".getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8
        );

        ResponseEntity<ExceptionModel> response = controller.InternalServerException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ExceptionModel body = response.getBody();
        assertNotNull(body);
        assertEquals("500 Error interno", body.getError().get(0).getDetail());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getError().get(0).getCodigo());
    }

    @Test
    public void testNoHandlerFoundException() {
        Exception ex = new NoHandlerFoundException("GET", "/ruta/invalida", null);

        ResponseEntity<ExceptionModel> response = controller.NoHandlerFoundExceptionException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ExceptionModel body = response.getBody();
        assertNotNull(body);
        assertTrue(body.getError().get(0).getDetail().contains("/ruta/invalida"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getError().get(0).getCodigo());
    }

    @Test
    public void testNotFoundException() {
        Exception ex = new NotFound("No encontrado");

        ResponseEntity<ExceptionModel> response = controller.NotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No encontrado", response.getBody().getError().get(0).getDetail());
    }
}
package com.globallogic.globallogic.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ExceptionModel> BadRequestException(Exception ex) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionModel> handleValidationErrors(MethodArgumentNotValidException  ex) {
        List<ExceptionModel.Error> errores = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> {
                    ExceptionModel.Error e = new ExceptionModel.Error();
                    e.setCodigo(HttpStatus.BAD_REQUEST.value());
                    e.setDetail("Campo '" + error.getField() + "': " + error.getDefaultMessage());
                    return e;
                })
                .collect(Collectors.toList());

        ExceptionModel response = new ExceptionModel();
        response.setError(errores);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<ExceptionModel> handleUnknownField(UnrecognizedPropertyException ex) {
        String mensaje = "Campo no reconocido: '" + ex.getPropertyName() + "'";

        ExceptionModel.Error error = new ExceptionModel.Error();
        error.setCodigo(HttpStatus.BAD_REQUEST.value());
        error.setDetail(mensaje);

        ExceptionModel response = new ExceptionModel();
        response.setError(Collections.singletonList(error));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ExceptionModel> NotFoundException(Exception ex) {
         return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionModel> ConflictException(Exception ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ExceptionModel> InternalServerException(Exception ex) {
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionModel> buildResponse(Exception ex, HttpStatus status) {
        ExceptionModel.Error error = new ExceptionModel.Error();
        error.setDetail(ex.getLocalizedMessage());
        error.setCodigo(status.value());

        ExceptionModel response = new ExceptionModel();
        response.setError(Collections.singletonList(error));

        return new ResponseEntity<>(response, status);
    }
}

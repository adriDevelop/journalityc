package com.adridevelop.journalytic_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class APIExceptionHandler {

    private ResponseEntity<ErrorResponseDTO> build (HttpStatus status, Exception excep, HttpServletRequest req){

        ErrorResponseDTO errorResponseDto = new ErrorResponseDTO(
            status.value(), status.getReasonPhrase(), excep.getMessage(), req.getRequestURI());

        return new ResponseEntity<ErrorResponseDTO>(errorResponseDto, status);
    }

    @ExceptionHandler(DireccionNoEncontradaException.class)
    public ResponseEntity<ErrorResponseDTO> direccionNoEncontrada(DireccionNoEncontradaException exception, HttpServletRequest req){
        return build(HttpStatus.BAD_REQUEST, exception, req);
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> usuarioNoEncontrado(UsuarioNoEncontradoException exception, HttpServletRequest req){
        return build(HttpStatus.BAD_REQUEST, exception, req);
    }

}

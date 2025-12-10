package com.example.cervezas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarRecursoNoEncontrado(
            RecursoNoEncontradoException ex, 
            WebRequest request) {
        
        ErrorResponse error = crearErrorResponse(
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            request
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ArchivoInvalidoException.class)
    public ResponseEntity<ErrorResponse> manejarArchivoInvalido(
            ArchivoInvalidoException ex, 
            WebRequest request) {
        
        ErrorResponse error = crearErrorResponse(
            HttpStatus.BAD_REQUEST,
            ex.getMessage(),
            request
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ErrorAlmacenamientoException.class)
    public ResponseEntity<ErrorResponse> manejarErrorAlmacenamiento(
            ErrorAlmacenamientoException ex, 
            WebRequest request) {
        
        ErrorResponse error = crearErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.getMessage(),
            request
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(
            MethodArgumentNotValidException ex, 
            WebRequest request) {
        
        String mensajesError = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
        
        ErrorResponse error = crearErrorResponse(
            HttpStatus.BAD_REQUEST,
            mensajesError,
            request
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarExcepcionGeneral(
            Exception ex, 
            WebRequest request) {
        
        ErrorResponse error = crearErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Error interno del servidor",
            request
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private ErrorResponse crearErrorResponse(HttpStatus status, String mensaje, WebRequest request) {
        return new ErrorResponse(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            mensaje,
            request.getDescription(false).replace("uri=", "")
        );
    }
}

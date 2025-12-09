package com.example.cervezas.exception;

public class ErrorAlmacenamientoException extends RuntimeException {
    
    public ErrorAlmacenamientoException(String mensaje) {
        super(mensaje);
    }
    
    public ErrorAlmacenamientoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

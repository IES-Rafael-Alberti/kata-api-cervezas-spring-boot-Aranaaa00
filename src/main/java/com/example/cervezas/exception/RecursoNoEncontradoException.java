package com.example.cervezas.exception;

public class RecursoNoEncontradoException extends RuntimeException {
    
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    
    public RecursoNoEncontradoException(String recurso, Long id) {
        super(String.format("%s no encontrado con ID: %d", recurso, id));
    }
    
    public RecursoNoEncontradoException(String recurso, String identificador) {
        super(String.format("%s no encontrado: %s", recurso, identificador));
    }
}

package com.universidad.proyecto.exception;

/**
 * Excepción personalizada para errores de solicitud incorrecta
 */
public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}


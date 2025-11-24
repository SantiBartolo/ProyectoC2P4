package com.universidad.proyecto.exception;

/**
 * Excepción personalizada cuando no se encuentra un estudiante
 */
public class StudentNotFoundException extends RuntimeException {
    
    public StudentNotFoundException(String message) {
        super(message);
    }
    
    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}


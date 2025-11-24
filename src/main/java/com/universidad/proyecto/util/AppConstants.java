package com.universidad.proyecto.util;

/**
 * Clase de constantes de la aplicación
 */
public class AppConstants {
    
    // Mensajes de error
    public static final String STUDENT_NOT_FOUND = "Estudiante no encontrado con id: %d";
    public static final String EMAIL_ALREADY_EXISTS = "El email %s ya está registrado";
    public static final String INVALID_EMAIL_FORMAT = "Formato de email inválido";
    public static final String INVALID_DATE_FORMAT = "Formato de fecha inválido";
    
    // Validaciones
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MAX_PROGRAM_LENGTH = 100;
    public static final int MAX_DOCUMENT_LENGTH = 20;
    
    // Rutas de API
    public static final String API_BASE_PATH = "/api/students";
    
    // PDF
    public static final String CERTIFICATE_TITLE = "CERTIFICADO DE ESTUDIANTE";
    public static final String CERTIFICATE_FONT = "Helvetica";
    
    private AppConstants() {
        // Clase de utilidad, no instanciable
    }
}


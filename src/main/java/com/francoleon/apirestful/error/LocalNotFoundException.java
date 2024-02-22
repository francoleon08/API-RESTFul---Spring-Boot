package com.francoleon.apirestful.error;

/**
 * Exception -> Local no encontrado
 */
public class LocalNotFoundException extends Exception{
    public LocalNotFoundException(String message) {
        super(message);
    }
}

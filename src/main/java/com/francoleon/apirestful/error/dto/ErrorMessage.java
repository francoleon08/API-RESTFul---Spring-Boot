package com.francoleon.apirestful.error.dto;

import org.springframework.http.HttpStatus;

/**
 * Plantilla para mostrar al cliente
 * Clase inmutable
 */
public record ErrorMessage(HttpStatus status, String mesagge) {}

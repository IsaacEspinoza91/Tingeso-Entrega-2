package com.kartingrm.reservas_comprobantes_service.config;


public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}

package com.kartingrm.cliente_desc_frecu_service.DTO;

public class DescuentoFrecuenciaResponse {

    private double porcentajeDescuento;


    // Constructores
    public DescuentoFrecuenciaResponse() {}
    public DescuentoFrecuenciaResponse(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    // Getters y setters
    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
}

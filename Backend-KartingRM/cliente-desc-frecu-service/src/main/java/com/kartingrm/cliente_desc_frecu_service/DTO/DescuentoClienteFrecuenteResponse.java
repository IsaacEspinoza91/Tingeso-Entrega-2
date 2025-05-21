package com.kartingrm.cliente_desc_frecu_service.DTO;

public class DescuentoClienteFrecuenteResponse {

    private double porcentajeDescuento;

    // Constructores
    public DescuentoClienteFrecuenteResponse() {}

    public DescuentoClienteFrecuenteResponse(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    // Metodos
    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
}

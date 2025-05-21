package com.kartingrm.descuento_grupo_service.DTO;

public class DescuentoGrupoResponse {

    private double porcentajeDescuento;


    // Constructores
    public DescuentoGrupoResponse() {}
    public DescuentoGrupoResponse(double porcentajeDescuento) {
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

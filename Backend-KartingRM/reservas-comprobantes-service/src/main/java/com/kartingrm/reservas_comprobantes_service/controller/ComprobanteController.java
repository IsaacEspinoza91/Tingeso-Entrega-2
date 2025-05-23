package com.kartingrm.reservas_comprobantes_service.controller;

import com.kartingrm.reservas_comprobantes_service.model.ComprobanteConDetallesDTO;
import com.kartingrm.reservas_comprobantes_service.service.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas-comprobantes-service/comprobantes")
public class ComprobanteController {

    @Autowired
    private ComprobanteService comprobanteService;

    // GET comprobante segun id
    @GetMapping("/{idComprobante}")
    public ComprobanteConDetallesDTO getComprobante(@PathVariable("idComprobante") Long idComprobante) {
        return comprobanteService.getComprobanteConDetallesById(idComprobante);
    }

    // CREATE comprobante con todos sus detalles
    // Deben estar previamente los integrantes asociados a la reserva (tabla cliente_reserva)
    @PostMapping("/reserva/{reservaId}/descuento-extra/{descuentoExtra}")
    public ComprobanteConDetallesDTO createComprobante(@PathVariable Long reservaId, @PathVariable double descuentoExtra) {
        return comprobanteService.createComprobante(reservaId, descuentoExtra);
    }
}

package com.kartingrm.reservas_comprobantes_service.controller;

import com.kartingrm.reservas_comprobantes_service.entity.Comprobante;
import com.kartingrm.reservas_comprobantes_service.model.ComprobanteConDetallesDTO;
import com.kartingrm.reservas_comprobantes_service.service.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas-comprobantes-service/comprobantes")
public class ComprobanteController {

    @Autowired
    private ComprobanteService comprobanteService;

    // GET comprobante segun id
    @GetMapping("/{idComprobante}")
    public ResponseEntity<ComprobanteConDetallesDTO> getComprobante(@PathVariable("idComprobante") Long idComprobante) {
        ComprobanteConDetallesDTO comprobante = comprobanteService.getComprobanteConDetallesById(idComprobante);
        return ResponseEntity.ok(comprobante);
    }

    // CREATE comprobante con todos sus detalles
    // Deben estar previamente los integrantes asociados a la reserva (tabla cliente_reserva)
    @PostMapping("/reserva/{reservaId}/descuento-extra/{descuentoExtra}")
    public ResponseEntity<ComprobanteConDetallesDTO> createComprobante(@PathVariable Long reservaId, @PathVariable double descuentoExtra) {
        ComprobanteConDetallesDTO comprobante = comprobanteService.createComprobante(reservaId, descuentoExtra);
        return ResponseEntity.ok(comprobante);
    }

    // UPDATE
    // Actualizar estado pagado de comprobante
    @PutMapping("/{id}/estado/{pagado}")
    public ResponseEntity<Comprobante> updatePagadoDeComprobante(@PathVariable Long id, @PathVariable boolean pagado) {
        Comprobante comprobante = comprobanteService.updateEstadoPagadoDeComprobante(id, pagado);
        return ResponseEntity.ok(comprobante);
    }

    // DELETE comprobante con todos sus detalles
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComprobante(@PathVariable Long id) {
        comprobanteService.deleteComprobante(id);
        return ResponseEntity.noContent().build();
    }
}

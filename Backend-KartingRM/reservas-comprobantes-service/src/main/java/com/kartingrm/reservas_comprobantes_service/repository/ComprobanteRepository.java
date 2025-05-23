package com.kartingrm.reservas_comprobantes_service.repository;

import com.kartingrm.reservas_comprobantes_service.entity.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {
}

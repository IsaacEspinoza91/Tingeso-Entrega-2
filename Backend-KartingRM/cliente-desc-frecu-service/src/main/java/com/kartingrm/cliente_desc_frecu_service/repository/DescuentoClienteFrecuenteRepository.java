package com.kartingrm.cliente_desc_frecu_service.repository;

import com.kartingrm.cliente_desc_frecu_service.entity.DescuentoClienteFrecuente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoClienteFrecuenteRepository extends JpaRepository<DescuentoClienteFrecuente, Long> {
}

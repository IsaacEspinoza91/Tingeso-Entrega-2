package com.kartingrm.cliente_desc_frecu_service.repository;

import com.kartingrm.cliente_desc_frecu_service.entity.ClienteReserva;
import com.kartingrm.cliente_desc_frecu_service.entity.ClienteReservaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteReservaRepository extends JpaRepository<ClienteReserva, ClienteReservaId> {

    // Encuentra por idCliente dentro del ID compuesto
    List<ClienteReserva> findById_IdCliente(Long idCliente);
}
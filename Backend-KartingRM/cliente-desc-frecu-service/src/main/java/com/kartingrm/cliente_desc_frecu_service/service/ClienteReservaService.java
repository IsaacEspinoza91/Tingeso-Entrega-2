package com.kartingrm.cliente_desc_frecu_service.service;

import com.kartingrm.cliente_desc_frecu_service.entity.ClienteReserva;
import com.kartingrm.cliente_desc_frecu_service.entity.ClienteReservaId;
import com.kartingrm.cliente_desc_frecu_service.repository.ClienteReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteReservaService {

    @Autowired
    private ClienteReservaRepository clienteReservaRepository;


    public List<ClienteReserva> getClientesReservas() {
        return clienteReservaRepository.findAll();
    }

    public ClienteReserva getClienteReserva(ClienteReservaId id) {
        Optional<ClienteReserva> clienteReserva = clienteReservaRepository.findById(id);
        if (clienteReserva.isEmpty()) throw new EntityNotFoundException("ClienteReserva no encontrado");

        return clienteReserva.get();
    }

    public List<ClienteReserva> obtenerReservasPorClienteId(Long idCliente) {
        return clienteReservaRepository.findById_IdCliente(idCliente);
    }

    public ClienteReserva createClienteReserva(ClienteReserva reserva) {
        if (reserva.getId() == null || reserva.getId().getIdReserva() == null || reserva.getId().getIdCliente() == null) {
            throw new IllegalArgumentException("El ID de reserva y el ID de cliente son obligatorios.");
        }
        if (clienteReservaRepository.existsById(reserva.getId())) {
            throw new IllegalArgumentException("Ya existe una reserva con el ID reserva " + reserva.getId().getIdReserva() + " e ID cliente " + reserva.getId().getIdCliente());
        }
        if (reserva.getEstado() == null) {
            reserva.setEstado("completada");
        }

        return clienteReservaRepository.save(reserva);
    }

    public ClienteReserva updateClienteReserva(ClienteReservaId id, ClienteReserva cliReser) {
        Optional<ClienteReserva> clienteReserva = clienteReservaRepository.findById(id);
        if (clienteReserva.isEmpty()) throw new EntityNotFoundException("ClienteReserva no encontrado");

        cliReser.setId(id);
        return clienteReservaRepository.save(cliReser);
    }

    public Boolean deleteClienteReserva(ClienteReservaId id) {
        Optional<ClienteReserva> clienteReserva = clienteReservaRepository.findById(id);
        if (clienteReserva.isEmpty()) throw new EntityNotFoundException("ClienteReserva no encontrado");

        clienteReservaRepository.deleteById(id);
        return true;
    }


}

package com.kartingrm.reservas_comprobantes_service.service;

import com.kartingrm.reservas_comprobantes_service.config.ServiceUnavailableException;
import com.kartingrm.reservas_comprobantes_service.entity.ClienteReserva;
import com.kartingrm.reservas_comprobantes_service.entity.ClienteReservaId;
import com.kartingrm.reservas_comprobantes_service.entity.Reserva;
import com.kartingrm.reservas_comprobantes_service.repository.ClienteReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteReservaService {

    @Autowired
    private ClienteReservaRepository clienteReservaRepository;
    @Autowired
    private ReservaService reservaService;


    public List<ClienteReserva> obtenerIntegrantesByIdReserva(Long idReserva) {
        return clienteReservaRepository.findByIdReserva(idReserva);
    }

    public boolean agregarIntegrante(Long idCliente, Long idReserva) {
        // Falta verificar existencia de cliente

        // Verificar si ya existe la relación
        if (clienteReservaRepository.existsByIdClienteAndIdReserva(idCliente, idReserva)) {
            throw new IllegalStateException("El cliente ya esta en esta reserva");
        }

        // Obtener cantidad de integrantes para la reserva
        Integer cantidadIntegrantes = reservaService.getReservaById(idReserva).getTotalPersonas();

        // Verificar límite de integrantes
        int integrantesActuales = clienteReservaRepository.countByIdReserva(idReserva);
        if (integrantesActuales >= cantidadIntegrantes) {
            throw new IllegalStateException("La reserva ha alcanzado el máximo de integrantes");
        }

        // Crear y guardar la relación
        ClienteReserva nuevaRelacion = new ClienteReserva(idCliente, idReserva);
        clienteReservaRepository.save(nuevaRelacion);
        return true;
    }

    public void quitarIntegrante(Long idCliente, Long idReserva) {
        ClienteReservaId id = new ClienteReservaId(idCliente, idReserva);
        if (!clienteReservaRepository.existsById(id)) {
            throw new IllegalArgumentException("El cliente no esta relacionado a esta reserva");
        }
        clienteReservaRepository.deleteById(id);
    }


}
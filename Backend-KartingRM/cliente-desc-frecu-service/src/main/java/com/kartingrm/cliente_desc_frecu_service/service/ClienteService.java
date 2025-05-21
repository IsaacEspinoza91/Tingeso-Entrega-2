package com.kartingrm.cliente_desc_frecu_service.service;

import com.kartingrm.cliente_desc_frecu_service.entity.Cliente;
import com.kartingrm.cliente_desc_frecu_service.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    public Cliente getCliente(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) throw new EntityNotFoundException("Cliente id " + id + " no encontrado");

        return cliente.get();
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) throw new EntityNotFoundException("Cliente id " + id + " no encontrado");

        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    public Boolean deleteCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) throw new EntityNotFoundException("Cliente id " + id + " no encontrado");

        clienteRepository.deleteById(id);
        return true;
    }

}

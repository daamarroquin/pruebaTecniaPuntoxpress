package com.puntoxpress.backend.service;



import com.puntoxpress.backend.model.Enties.Client;
import com.puntoxpress.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client crear(Client infoRequest) {
        return clientRepository.save(infoRequest);
    }

    public Page<Client> obtenerLista(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client actualizar(UUID id, Client infoRequest) {
        if (clientRepository.existsById(id)) {
            infoRequest.setId(id);
            return clientRepository.save(infoRequest);
        }
        return null;
    }

    public boolean eliminar(UUID id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Client buscarPorId(UUID id) {
        return clientRepository.findById(id).orElse(null);
    }
}

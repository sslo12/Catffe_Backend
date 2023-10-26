package com.catffe.apiweb.service;

import com.catffe.apiweb.model.ClientesModel;
import com.catffe.apiweb.repository.IClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientesServiceImp implements IClientesService {

    @Autowired
    IClientesRepository clientesRepository;
    @Override
    public String guardarCliente(ClientesModel cliente) {
        clientesRepository.save(cliente);
        return "El cliente con el id: "+cliente.getId()+" fue creado exitosamente";
    }

    @Override
    public List<ClientesModel> listarClientes() {
        return clientesRepository.findAll();
    }

    @Override
    public Optional<ClientesModel> obtenerClientePorId(int clienteId) {
        return clientesRepository.findById(clienteId);
    }

    @Override
    public String eliminarClientePorId(int clienteId) {
        clientesRepository.deleteById(clienteId);
        return "El cliente con el id: "+clienteId+" fue eliminado exitosamente";
    }

    @Override
    public String actualizarCliente(ClientesModel cliente) {
        clientesRepository.save(cliente);
        return "El cliente con el id: "+cliente.getId()+" fue actualizado exitosamente";
    }

    @Override
    public String mascotas(ClientesModel cliente) {
        clientesRepository.save(cliente);
        return null;
    }
}

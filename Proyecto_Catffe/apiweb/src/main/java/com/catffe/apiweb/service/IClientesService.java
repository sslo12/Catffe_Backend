package com.catffe.apiweb.service;

import com.catffe.apiweb.model.ClientesModel;

import java.util.List;
import java.util.Optional;

public interface IClientesService {

    String guardarCliente(ClientesModel cliente);
    List<ClientesModel> listarClientes();
    Optional<ClientesModel> obtenerClientePorId(int clienteId);
    String eliminarClientePorId(int clienteId);
    String actualizarCliente(ClientesModel cliente);
    String mascotas(ClientesModel cliente);

}

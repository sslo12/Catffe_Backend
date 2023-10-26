package com.catffe.apiweb.controller;

import com.catffe.apiweb.exception.CamposInvalidosException;
import com.catffe.apiweb.exception.RecursoNoEncontradoException;
import com.catffe.apiweb.model.ClientesModel;
import com.catffe.apiweb.model.MascotasModel;
import com.catffe.apiweb.service.IClientesService;
import com.catffe.apiweb.service.IMascotasService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catffe/clientes")
public class ClientesController {

    @Autowired
    IClientesService clientesService;


    @PostMapping("/insert")
    public ResponseEntity<String> crearCliente(@RequestBody ClientesModel cliente){
        clientesService.guardarCliente(cliente);
        return new ResponseEntity<String>(clientesService.guardarCliente(cliente), HttpStatus.OK);
    }


    @GetMapping("/all/list")
    public ResponseEntity<List<ClientesModel>>mostarClientes(){
        return new ResponseEntity<List<ClientesModel>>(clientesService.listarClientes(),HttpStatus.OK);
    }


    @GetMapping ("/find/{id}")
    public ResponseEntity<ClientesModel>buscarClientePorId(@PathVariable int id){
        ClientesModel cliente = clientesService.obtenerClientePorId(id).
                orElseThrow(()->new RecursoNoEncontradoException("Error! no se encontro el cliente con el id "+id));

        return ResponseEntity.ok(cliente);
    }


    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String>eliminarClientePorId(@PathVariable int id){
        ClientesModel cliente = clientesService.obtenerClientePorId(id).
                orElseThrow(()-> new RecursoNoEncontradoException("Error! no se encontro el cliente con el id "+id));

        return new ResponseEntity<String>(clientesService.eliminarClientePorId(cliente.getId()),HttpStatus.OK);
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<String> actualizarClientePorId(@PathVariable int id, @RequestBody ClientesModel detallesCliente) {
        ClientesModel cliente = clientesService.obtenerClientePorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! no se encontró el cliente con el id " + id));

        int telefono = detallesCliente.getTelefono();

        if (String.valueOf(telefono).length() != 10) {
            throw new CamposInvalidosException("Error! El número de teléfono debe tener 10 dígitos.");
        }

        String nombreActualizar = detallesCliente.getNombre();
        String apellidoActualizar = detallesCliente.getApellido();
        String correoActualizar = detallesCliente.getCorreo();

        if (nombreActualizar != null && !nombreActualizar.isEmpty() &&
                apellidoActualizar != null && !apellidoActualizar.isEmpty() &&
                correoActualizar != null && !correoActualizar.isEmpty()) {

            // Asignamos los valores que vamos a actualizar del usuario
            cliente.setNombre(nombreActualizar);
            cliente.setApellido(apellidoActualizar);
            cliente.setCorreo(correoActualizar);
            cliente.setTelefono(telefono);

            // Guardamos los cambios
            clientesService.actualizarCliente(cliente);
            return new ResponseEntity<String>("El cliente con el id: " + id + " fue actualizado exitosamente", HttpStatus.OK);
        } else {
            throw new CamposInvalidosException("Error! Asegúrate de que el nombre, el apellido, el correo y el teléfono no estén vacíos.");
        }
    }
}

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

@RestController
@RequestMapping("/catffe/mascotas")
public class MascotasController {

    @Autowired
    IMascotasService mascotasService;
    @Autowired
    IClientesService clientesService;


    @PostMapping("/insertar")
    public ResponseEntity<String> crearMascota(@RequestBody MascotasModel mascota){
        mascotasService.guardarMascota(mascota);
        int IdClienteRecuperado = mascota.getCliente_id();
        ClientesModel ClienteRecuperado = this.clientesService.obtenerClientePorId(IdClienteRecuperado).
                orElseThrow(()->new RecursoNoEncontradoException("Error! el cliente no existe "));

        // Documento de mascota
        DBObject nuevaMascota = new BasicDBObject();
        nuevaMascota.put("nombreMascota", mascota.getNombre());
        nuevaMascota.put("mascota_id", mascota.getId());

        ClienteRecuperado.agregarMascota(nuevaMascota);
        this.clientesService.actualizarCliente(ClienteRecuperado);
        System.out.println("Agregamos la o las mascotas al cliente "+IdClienteRecuperado);
        return new ResponseEntity<String>(mascotasService.guardarMascota(mascota), HttpStatus.OK);
    }


    @GetMapping("/listar")
    public ResponseEntity<List<MascotasModel>>mostrarMascotas(){
        return new ResponseEntity<List<MascotasModel>>(mascotasService.listarMascotas(),HttpStatus.OK);
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<MascotasModel>obtenerMascotaporId(@PathVariable int id){
        MascotasModel mascota = mascotasService.obtenerMascotaporId(id).
                orElseThrow(()->new RecursoNoEncontradoException("Error! no se encontró la mascota con el id "+id));

        return ResponseEntity.ok(mascota);
    }


    @DeleteMapping ("/eliminar/{id}")
    public ResponseEntity<String>eliminarMascotaPorID(@PathVariable int id){
        MascotasModel mascota = mascotasService.obtenerMascotaporId(id).
                orElseThrow(()-> new RecursoNoEncontradoException("Error! no se encontró la mascota con el id "+id));

        return new ResponseEntity<String>(mascotasService.eliminarMascotaPorId(mascota.getId()),HttpStatus.OK);
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarMascotaPorId(@PathVariable int id, @RequestBody MascotasModel detalleMascota){
        MascotasModel mascota = mascotasService.obtenerMascotaporId(id).
                orElseThrow(()->new RecursoNoEncontradoException("Error! no se encontró la mascota con el id "+id));

        String nombreActualizar = detalleMascota.getNombre();
        if (nombreActualizar != null && !nombreActualizar.isEmpty()){
            //asignamos los valores que vamos actualizar de la mascota
            mascota.setNombre(nombreActualizar);
            //guardamos los cambios
            mascotasService.actualizarMascotaPorId(mascota);
            return new ResponseEntity<String>(mascotasService.actualizarMascotaPorId(mascota),HttpStatus.OK);
        }
        else{
            throw new CamposInvalidosException("Error! El nombre no puede estar vacío");
        }
    }

}

package com.catffe.apiweb.controller;

import com.catffe.apiweb.exception.RecursoNoEncontradoException;
import com.catffe.apiweb.model.enums.TipoUsuario;
import com.catffe.apiweb.model.TrabajadorModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.catffe.apiweb.service.ITrabajadorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catffe/Trabajador")
public class TrabajadorController {

    @Autowired
    ITrabajadorService trabajadorService;

    @PostMapping("/insert")
    public ResponseEntity<String> crearTrabajadores(@RequestBody List<TrabajadorModel> trabajadores) {
        for (TrabajadorModel trabajador : trabajadores) {
            TrabajadorModel.Credenciales credenciales = new TrabajadorModel.Credenciales();
            credenciales.setUsuario(trabajador.getCredenciales().getUsuario());
            credenciales.setTipoUsuario(trabajador.getCredenciales().getTipoUsuario());
            credenciales.setContrasena(trabajador.getCredenciales().getContrasena());

            trabajador.setCredenciales(credenciales);
            trabajadorService.guardarTrabajador(trabajador);
        }

        return new ResponseEntity<>("Trabajadores creados exitosamente", HttpStatus.OK);
    }


    @GetMapping("/all/list")
    public ResponseEntity<List<TrabajadorModel>>mostrarTrabajadores(){
        return new ResponseEntity<List<TrabajadorModel>>(trabajadorService.ListarTrabajadores(),HttpStatus.OK);
    }

    @GetMapping ("/find/{id}")
    public ResponseEntity<TrabajadorModel>buscarTrabajadorPorId(@PathVariable int id){
        TrabajadorModel Trabajador = trabajadorService.obtenerTrabajadorporID(id).
                orElseThrow(()->new RecursoNoEncontradoException("Error! no se encontro el cliente con el id "+id));

        return ResponseEntity.ok(Trabajador);
    }


    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String>eliminarTrabajadorPorId(@PathVariable int id){
        TrabajadorModel Trabajador = trabajadorService.obtenerTrabajadorporID(id).
                orElseThrow(()-> new RecursoNoEncontradoException("Error! no se encontro el cliente con el id "+id));

        return new ResponseEntity<String>(trabajadorService.eliminarTrabajadorPorID(Trabajador.getId()),HttpStatus.OK);
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<String> actualizarTrabajador(@PathVariable int id, @RequestBody TrabajadorModel detallesTrabajador) {
        TrabajadorModel trabajador = trabajadorService.obtenerTrabajadorporID(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! no se encontró el trabajador con el id " + id));

        // Validación de campos
        String nombreTActualizar = detallesTrabajador.getNombre();
        String apellidoTActualizar = detallesTrabajador.getApellido();
        TrabajadorModel.Credenciales credencialesTActualizar = detallesTrabajador.getCredenciales();

        if (nombreTActualizar == null || nombreTActualizar.trim().isEmpty() ||
                apellidoTActualizar == null || apellidoTActualizar.trim().isEmpty()) {
            throw new RecursoNoEncontradoException("Error! Asegúrate de que el nombre y el apellido no estén vacíos.");
        }

        // Asignamos los valores que vamos a actualizar del trabajador
        trabajador.setNombre(nombreTActualizar);
        trabajador.setApellido(apellidoTActualizar);

        // Actualizamos las credenciales si no son nulas y no están vacías
        if (credencialesTActualizar != null &&
                credencialesTActualizar.getUsuario() != null && !credencialesTActualizar.getUsuario().trim().isEmpty() &&
                credencialesTActualizar.getContrasena() != null && !credencialesTActualizar.getContrasena().trim().isEmpty()) {
            trabajador.setCredenciales(credencialesTActualizar);
        }

        // Guardamos los cambios
        trabajadorService.actualizarTrabajador(trabajador);
        return new ResponseEntity<>("El trabajador con el id: " + id + " fue actualizado exitosamente", HttpStatus.OK);
    }
    }

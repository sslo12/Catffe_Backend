package com.catffe.apiweb.controller;

import com.catffe.apiweb.exception.RecursoNoEncontradoException;
import com.catffe.apiweb.model.ProductosModel;
import com.catffe.apiweb.model.VentasModel;
import com.catffe.apiweb.service.IProductosService;
import com.catffe.apiweb.service.IVentaService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catffe/ventas")

public class TrabajadorController {
    @Autowired
    private IVentaService ventaService;
    @Autowired
    private IProductosService productosService;

    @PostMapping("/insert")
    public ResponseEntity<String> crearTrabajador(@RequestBody TrabajadorModel trabajador){
        // Documento de credenciales
        DBObject nuevaCredencial = new BasicDBObject();
        nuevaCredencial.put("usuario",trabajador.getUsuario());
        if (trabajador.getTipoTrabajador()==1)
            nuevaCredencial.put("tipoUsuario", TipoUsuario.ADMIN);
        else
            nuevaCredencial.put("tipoUsuario", TipoUsuario.EMPLEADO;
        nuevaCredencial.put("contrasena", trabajador.getContrasena());
        trabajador.setCredenciales(nuevaCredencial);
        trabajadorService.guardarTrabajador(trabajador);
        return new ResponseEntity<String>(trabajadorService.guardarTrabajador(trabajador), HttpStatus.OK);
    }
}

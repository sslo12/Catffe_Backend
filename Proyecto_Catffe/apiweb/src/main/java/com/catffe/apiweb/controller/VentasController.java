package com.catffe.apiweb.controller;

import com.catffe.apiweb.dominio.VentasDTO;
import com.catffe.apiweb.exception.RecursoNoEncontradoException;
import com.catffe.apiweb.model.ProductosModel;
import com.catffe.apiweb.model.VentasModel;
import com.catffe.apiweb.service.IProductosService;
import com.catffe.apiweb.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catffe/ventas")
public class VentasController {
    @Autowired
    IVentaService ventaService;
    @Autowired
    IProductosService productosService;

    private final IVentasService ventasService;

    @Autowired
    public VentasController(IVentasService ventasService) {
        this.ventasService = ventasService;
    }

    @PostMapping("/insert")
    public String insertVenta(@RequestBody VentasModel venta) {
        ventasService.insertVenta(venta);
        // Puedes realizar alguna validación de datos si es necesario antes de guardar
        // Luego, llama al servicio para guardar la venta
        return ventasService.insertVenta(venta);
    }
    
    @GetMapping("/todas/listar")
    public ResponseEntity<List<VentasModel>> listarVentas(){
        return new ResponseEntity<List<VentasModel>>(ventasService.listarVentas(), HttpStatus.OK);
    }

    @GetMapping("/porTrabajador/{trabajadorId}")
    public ResponseEntity<List<VentasModel>> obtenerVentasPorTrabajador(@PathVariable int trabajadorId) {
        List<VentasModel> ventas = ventasService.obtenerVentasPorTrabajador(trabajadorId);
        if(ventas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

}

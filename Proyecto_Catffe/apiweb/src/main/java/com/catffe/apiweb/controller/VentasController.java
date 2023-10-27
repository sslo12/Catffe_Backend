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

    @PostMapping("/insert")
    public ResponseEntity<String> crearVenta(@RequestBody VentasDTO ventadto) {
        VentasModel ventas = new VentasModel();
        //ventas.setId_venta(ventadto.getId_venta());
        Date fecha_hora = new Date();
        ventas.setCliente_id(ventadto.getCliente_id());
        ventas.setTrabajador_id(ventadto.getTrabajador_id());

        // Validar la existencia y disponibilidad del producto
        List<Map<String, Integer>> detalles = ventadto.getDetalles();
        for (Map<String, Integer> item : detalles) {
            int idProducto = item.get("id_producto");
            int cantidadSolicitada = item.get("cantidad");

            ProductosModel producto = this.productosService.obtenerProductoPorId(idProducto).orElse(null);
            if (producto == null) {
                return new ResponseEntity<String>("Producto no encontrado para el ID proporcionado", HttpStatus.BAD_REQUEST);
            }

            if (cantidadSolicitada > producto.getCantidad_dispo()) {
                return new ResponseEntity<String>("Cantidad insuficiente del producto en stock", HttpStatus.BAD_REQUEST);
            }

            // Resto de la lógica de procesamiento de ventas
            int CantidadActual = producto.getCantidad_dispo();
            double productoPrecio = producto.getPrecio();

            producto.setCantidad_dispo(CantidadActual - cantidadSolicitada);
            productosService.actualizarProducto(producto);

            double totalPrecioProducto = productoPrecio * cantidadSolicitada;

            double TotalPrecioActual = ventadto.getPrecio_total();
            ventadto.setPrecio_total(TotalPrecioActual + totalPrecioProducto);
            ventadto.setPrecio_total(ventadto.getPrecio_total());

            ProductosModel nuevodetalle = new ProductosModel();
            nuevodetalle.setId(idProducto);
            nuevodetalle.setCantidad_dispo(CantidadActual);
        }

        // Insertar el objeto ventas en la colección "venta"
        ventaService.crearVenta(ventas);
        return new ResponseEntity<String>("La venta se ha creado con éxito", HttpStatus.OK);
    }

    @GetMapping("/todas/listar")
    public ResponseEntity<List<VentasModel>>listarVentas(){
        return new ResponseEntity<List<VentasModel>>(ventaService.listarVentas(),HttpStatus.OK);
    }

    @GetMapping ("/find/{id}")
    public ResponseEntity<VentasModel>obtenerVentaPorId(@PathVariable int id){
        VentasModel ventas = ventaService.obtenerVentaPorId(id).
                orElseThrow(()->new RecursoNoEncontradoException("Error: no se encontró la venta con el ID " +id));

        return ResponseEntity.ok(ventas);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String>eliminarVentaPorId(@PathVariable int id){
        VentasModel venta = ventaService.obtenerVentaPorId(id).
                orElseThrow(()-> new RecursoNoEncontradoException("Error: no se encontró la venta con el ID " +id));

        return new ResponseEntity<String>(ventaService.eliminarVentaPorId(venta.getId_venta()),HttpStatus.OK);
    }

}
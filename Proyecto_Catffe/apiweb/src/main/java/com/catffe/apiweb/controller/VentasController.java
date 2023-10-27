package com.catffe.apiweb.controller;

import com.catffe.apiweb.dominio.VentasDTO;
import com.catffe.apiweb.exception.RecursoNoEncontradoException;
import com.catffe.apiweb.model.ProductosModel;
import com.catffe.apiweb.model.VentasModel;
import com.catffe.apiweb.service.IProductosService;
import com.catffe.apiweb.service.IVentaService;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catffe/ventas")
public class VentasController {

    @Autowired
    private IVentaService ventaService;

    @Autowired
    IProductosService productosService;

    @PostMapping("/insert")
    public ResponseEntity<String> crearVenta(@RequestBody VentasDTO ventadto) {
        VentasModel ventas = new VentasModel ();
        ventas.setId(ventadto.getId());
        ventas.setFecha_hora(ventadto.getFecha_hora());
        ventas.setCliente_id(ventadto.getCliente_id());
        ventas.setTrabajador_id(ventadto.getTrabajador_id());

        //Garantizar que existan productos y cantidades del detalle de la venta, si existen
        List<Map<String, Integer>> detalles = ventadto.getDetalles();
        ProductosModel producto = new ProductosModel();
        int IdProducto;
        int cantidad;
        for (int i = 0 ; i < detalles.size() ; i++) {
            IdProducto = detalles.get(i).get("id_producto");
            producto = this.productosService.obtenerProductoPorId(IdProducto).orElseThrow(()-> new RecursoNoEncontradoException("El producto no existe") );
            cantidad = detalles.get(i).get("cantidad");
                    if (producto != null && cantidad <= producto.getCantidad_dispo()) {

                        //En el objeto de detalle adicionar el id de Venta
                        int CantidadActual = producto.getCantidad_dispo();
                        double productoPrecio = producto.getPrecio();

                        producto.setCantidad_dispo(CantidadActual - cantidad);
                        productosService.actualizarProducto(producto);

                        double totalPrecioProducto = productoPrecio * cantidad;
                        double TotalPrecioActual = ventadto.getPrecio_total();

                        ventadto.setPrecio_total(TotalPrecioActual + totalPrecioProducto);
                        ventadto.setPrecio_total(ventadto.getPrecio_total());
                        ventaService.guardarVenta(ventas);

                        ProductosModel nuevodetalle = new ProductosModel();

                        nuevodetalle.setId(producto.getId());
                        nuevodetalle.setCantidad_dispo(CantidadActual);

                        System.out.print("id_producto " + IdProducto);
                        System.out.print("cantidad " + cantidad);
                        System.out.print("precio_total " + ventadto.getPrecio_total());
                    }
                }
                // De shoppingCart recuperar cada detalle de compra
                // Si no existen productos o cantidades del detalle de la venta, retornar un error
                return new ResponseEntity<String>(ventaService.guardarVenta(ventas),HttpStatus.OK);
            }

    /*@PostMapping("/insert")
    public ResponseEntity<String> crearVenta(@RequestBody VentasDTO ventadto) {
        VentasModel ventas = new VentasModel();
        ventas.setId(ventadto.getId());
        ventas.setCliente_id(ventadto.getCliente_id());
        ventas.setTrabajador_id(ventadto.getTrabajador_id());

        // Inicializar el precio total en 0
        double precioTotal = 0.0;
        int IdProducto = 0;
        int cantidad = 0;

        // Garantizar que existan productos y cantidades del detalle de la venta, si existen
        List<Map<String, Integer>> detalles = ventadto.getDetalles();
        for (Map<String, Integer> detalle : detalles) {
            IdProducto = detalle.get("id_producto");
            cantidad = detalle.get("cantidad");

            // Buscar el producto por ID
            ProductosModel producto = productosService.obtenerProductoPorId(IdProducto)
                    .orElseThrow(() -> new RecursoNoEncontradoException("El producto no existe"));

            // Verificar si hay suficiente cantidad disponible
            if (cantidad < producto.getCantidad_dispo()) {
                double productoPrecio = producto.getPrecio();

                // Actualizar el inventario del producto
                producto.setCantidad_dispo(producto.getCantidad_dispo() - cantidad);
                productosService.actualizarProducto(producto);

                // Calcular el precio total para este producto en el detalle de venta
                double totalPrecioProducto = productoPrecio * cantidad;
                precioTotal += totalPrecioProducto;}
            else {
                // Si no hay suficiente cantidad disponible, devolver un error
                return new ResponseEntity<>("No hay suficiente cantidad disponible para el producto con ID " + IdProducto, HttpStatus.BAD_REQUEST);
            }
        }

        // Asignar el precio total calculado a la venta
        ventas.setPrecio_total(precioTotal);

        // Guardar la venta en la base de datos
        String resultado = ventaService.guardarVenta(ventas);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }*/

    @GetMapping("/todas/listar")
    public ResponseEntity<List<VentasModel>>listarVentas(){
        return new ResponseEntity<List<VentasModel>>(ventaService.listarVentas(),HttpStatus.OK);
    }

    @GetMapping ("/find/{id}")
    public ResponseEntity<VentasModel>obtenerVentaPorId(@PathVariable int id){
        VentasModel venta = ventaService.obtenerVentaPorId(id).
                orElseThrow(()->new RecursoNoEncontradoException("Error: no se encontró la venta con el ID " +id));

        return ResponseEntity.ok(venta);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String>eliminarVentaPorId(@PathVariable int id){
        VentasModel venta = ventaService.obtenerVentaPorId(id).
                orElseThrow(()-> new RecursoNoEncontradoException("Error: no se encontró la venta con el ID " +id));

        return new ResponseEntity<String>(ventaService.eliminarVentaPorId(venta.getId()),HttpStatus.OK);
    }

}

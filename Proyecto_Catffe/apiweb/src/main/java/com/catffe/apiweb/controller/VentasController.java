package com.catffe.apiweb.controller;

import com.catffe.apiweb.model.ProductosModel;
import com.catffe.apiweb.model.VentaModel;
import com.catffe.apiweb.service.IProductosService;
import com.catffe.apiweb.service.IVentaService;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/catffe/ventas")
public class VentaController<Producto, Productos> {

    @Autowired
    private IVentaService ventaService;
    @Autowired
    private IProductosService productoService;

    @PostMapping("/insert")
    public ResponseEntity<String> crearVenta(@RequestBody VentaModel venta) {
        // Verificar si el producto está disponible
        boolean productoDisponible = productoService.verificarDisponibilidad(venta.getDetalles());

        if (productoDisponible) {
            // Restar productos vendidos del stock
            productoService.restarProductos(venta.getDetalles());

            // Guardar venta
            String result = ventaService.crearVenta(venta);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("El producto no está disponible", HttpStatus.BAD_REQUEST);
        }
    }















    @PostMapping("/all/insertar")
    public String insertarVenta(@RequestBody VentaModel venta) {
        try {
            // Paso 1: Obtener productos actuales
            Map<Integer, Producto> productosActuales = new HashMap<>();
            for (DBObject detalle : venta.getDetalles()) {
                int idProducto = (int) detalle.get("id_producto");
                Producto productoActual = (Producto) productoService.obtenerProductoPorId(idProducto);
                if (productoActual == null) {
                    return "El producto con ID " + idProducto + " no existe.";
                }
                productosActuales.put(idProducto, productoActual);
            }

            // Paso 2: Actualizar las cantidades
            for (DBObject detalle : venta.getDetalles()) {
                int idProducto = (int) detalle.get("id_producto");
                detalle.get("nombre");
                int cantidad = (int) detalle.get("cantidad");
                Producto productoActual = productosActuales.get(idProducto);

                if (productoActual.getCantidad() < cantidad) {
                    return "El producto con ID " + idProducto + " no tiene suficiente stock para completar la venta.";
                }

                productoActual.setCantidadDipo(productoActual.get() - cantidad);
                productoService.actualizarProducto((ProductosModel) productoActual);
            }

            // Paso 3: Guardar la venta
            ventaService.crearVenta(venta);

            return "Venta realizada con éxito.";
        }
    }

    @GetMapping("/all/list")
    public ResponseEntity<List<VentasModel>> mostrarVentas() {
        return new ResponseEntity<List<VentasModel>>(ventaService.listarVentas(),HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<VentasModel> buscarVentaPorId(@PathVariable String id) {
        VentasModel venta = ventaService.obtenerVentaPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error: no se encontró venta con el ID " + id));

        return ResponseEntity.ok(venta);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminarVentaPorId(@PathVariable String id) {
        VentasModel venta = ventaService.obtenerVentaPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error: no se encontró la venta con el ID " + id));

        ventaService.eliminarVentaPorId(id);

        return new ResponseEntity<>("La venta con el ID " + id + " ha sido eliminada exitosamente", HttpStatus.OK);
    }
}

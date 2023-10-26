package com.catffe.apiweb.controller;

import com.catffe.apiweb.exception.CamposInvalidosException;
import com.catffe.apiweb.exception.RecursoNoEncontradoException;
import com.catffe.apiweb.model.ProductosModel;
import com.catffe.apiweb.service.IProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catffe/productos")
public class ProductosController {

    @Autowired
    IProductosService productosService;

    @PostMapping("/insert")
    public ResponseEntity<String> crearProducto(@RequestBody ProductosModel producto) {
        productosService.guardarProducto(producto);
        return new ResponseEntity<String>("Producto creado exitosamente", HttpStatus.OK);
    }

    @GetMapping("/all/list")
    public ResponseEntity<List<ProductosModel>> listarProductos() {
        List<ProductosModel> productos = productosService.listarProductos();
        return new ResponseEntity<List<ProductosModel>>(productos, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductosModel> buscarProductoPorId(@PathVariable int id) {
        ProductosModel producto = productosService.obtenerProductoPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el producto con el ID " + id));

        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminarProductoPorId(@PathVariable int id) {
        ProductosModel producto = productosService.obtenerProductoPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el producto con el ID " + id));

        productosService.eliminarProductoPorId(producto.getId());
        return new ResponseEntity<String>("Producto eliminado exitosamente", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> actualizarProductoPorId(@PathVariable int id, @RequestBody ProductosModel detallesProducto) {
        ProductosModel producto = productosService.obtenerProductoPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el producto con el ID " + id));

        // Se obtienen los nuevos valores de los atributos desde el objeto detallesProducto
        double precioActualizar = detallesProducto.getPrecio();
        String nombreActualizar = detallesProducto.getNombre();
        String descripcionActualizar = detallesProducto.getDescripcion();
        String origenActualizar = detallesProducto.getOrigen();
        String generoActualizar = detallesProducto.getGenero();
        String autorActualizar = detallesProducto.getAutor();
        // Supongo que aquí deseas obtener los nuevos valores de comboProductos

        // Se verifica si el nuevo precio no es nulo y no está vacío (es un valor numérico)
        if (precioActualizar > 0) {
            producto.setPrecio(precioActualizar);

            // Luego, se actualizan los demás atributos si es necesario, como nombre, descripción, origen, etc.
            producto.setNombre(nombreActualizar);
            producto.setDescripcion(descripcionActualizar);
            producto.setOrigen(origenActualizar);
            producto.setGenero(generoActualizar);
            producto.setAutor(autorActualizar);
            // Actualización de comboProductos si es necesario

            // Finalmente, se guarda la actualización
            productosService.actualizarProducto(producto);
            return new ResponseEntity<String>("El producto con el ID " + id + " fue actualizado exitosamente", HttpStatus.OK);
        } else {
            throw new CamposInvalidosException("Error! Asegúrate de que el precio sea un número válido y mayor que 0.");
        }
    }

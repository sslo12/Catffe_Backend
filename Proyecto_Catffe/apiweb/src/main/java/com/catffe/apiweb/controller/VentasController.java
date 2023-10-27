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
                    if (producto != null && cantidad <= producto.getCantidadDispo()) {

                        //En el objeto de detalle adicionar el id de Venta
                        int CantidadActual = producto.getCantidadDispo();
                        double productoPrecio = producto.getPrecio();

                        producto.setCantidadDispo(CantidadActual - cantidad);
                        productosService.actualizarProducto(producto);

                        double totalPrecioProducto = productoPrecio * cantidad;
                        double TotalPrecioActual = ventadto.getPrecio_total();

                        ventadto.setPrecio_total(TotalPrecioActual + totalPrecioProducto);
                        ventadto.setPrecio_total(ventadto.getPrecio_total());
                        ventaService.guardarVenta(ventas);

                        ProductosModel nuevodetalle = new ProductosModel();

                        nuevodetalle.setId(producto.getId());
                        nuevodetalle.setCantidadDispo(CantidadActual);

                        System.out.print("id_producto " + IdProducto);
                        System.out.print("cantidad " + cantidad);
                        System.out.print("precio_total " + ventadto.getPrecio_total());
                    }
                }
                // De shoppingCart recuperar cada detalle de compra
                // Si no existen productos o cantidades del detalle de la venta, retornar un error
                return new ResponseEntity<String>(ventaService.guardarVenta(ventas),HttpStatus.OK);
            }

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

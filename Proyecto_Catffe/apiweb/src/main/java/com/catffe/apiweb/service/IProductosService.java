package com.catffe.apiweb.service;

import com.catffe.apiweb.model.ProductosModel;
import com.mongodb.DBObject;

import java.util.List;
import java.util.Optional;

public interface IProductosService {

    void guardarProducto(ProductosModel producto);
    List<ProductosModel> listarProductos();
    Optional<ProductosModel> obtenerProductoPorId(int productoId);
    void eliminarProductoPorId(int productoId);
    void actualizarProducto(ProductosModel producto);
    void restarProductos(List<DBObject> detalles);
    boolean verificarDisponibilidad(List<DBObject> detalles);
}

package com.catffe.apiweb.service;

import com.catffe.apiweb.model.ProductosModel;
import com.catffe.apiweb.repository.IProductosRepository;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosServiceImp implements IProductosService {

    private final IProductosRepository productosRepository;

    @Autowired
    public ProductosServiceImp(IProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    @Override
    public void guardarProducto(ProductosModel producto) {
        productosRepository.save(producto);
    }

    @Override
    public void actualizarProducto(ProductosModel producto) {
        productosRepository.save(producto);
    }

    @Override
    public void restarProductos(List<DBObject> detalles) {

    }

    @Override
    public boolean verificarDisponibilidad(List<DBObject> detalles) {
        return false;
    }

    @Override
    public void agregarComboAlProducto(int productoId, ProductosModel.ComboProducto combo) {

    }

    @Override
    public List<ProductosModel.ComboProducto> listarCombosDelProducto(int productoId) {
        return null;
    }

    @Override
    public void eliminarComboDelProducto(int productoId, int comboId) {

    }

    @Override
    public void actualizarComboDelProducto(int productoId, int comboId, ProductosModel.ComboProducto combo) {

    }

    @Override
    public void eliminarProductoPorId(int productoId) {
        productosRepository.deleteById(productoId);
    }

    @Override
    public List<ProductosModel> listarProductos() {
        return productosRepository.findAll();
    }

    @Override
    public Optional<ProductosModel> obtenerProductoPorId(int productoId) {
        return productosRepository.findById(productoId);}
    }
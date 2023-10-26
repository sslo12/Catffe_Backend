package com.catffe.apiweb.service;

import com.catffe.apiweb.model.ProductosModel;
import com.catffe.apiweb.repository.IProductosRepository;
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
    public void eliminarProductoPorId(int productoId) {
        productosRepository.deleteById(productoId);
    }

    @Override
    public List<ProductosModel> listarProductos() {
        return productosRepository.findAll();
    }

    @Override
    public Optional<ProductosModel> obtenerProductoPorId(int productoId) {
        return productosRepository.findById(productoId);
    }
}
package com.catffe.apiweb.service;

import com.catffe.apiweb.model.VentaModel;
import com.catffe.apiweb.model.VentasModel;
import com.catffe.apiweb.repository.IVentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImp implements IVentaService {
    @Autowired
    IVentasRepository ventasRepository;

    @Override
    public String crearVenta(VentaModel venta) {
        ventasRepository.save(venta);
        return "La venta con el id: " + venta.getId() + " fue creada exitosamente";
    }

    @Override
    public List<VentasModel> listarVentas() {
        return ventasRepository.findAll();
    }

    @Override
    public Optional<VentasModel> obtenerVentaPorId(String ventaId) {
        return ventasRepository.findById(ventaId);
    }

    @Override
    public void eliminarVentaPorId(String ventaId) {
        ventasRepository.deleteById(ventaId);
    }
}

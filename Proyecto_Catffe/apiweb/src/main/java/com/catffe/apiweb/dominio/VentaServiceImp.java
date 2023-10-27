package com.catffe.apiweb.dominio;

import com.catffe.apiweb.model.VentasModel;
import com.catffe.apiweb.repository.IVentasRepository;
import com.catffe.apiweb.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImp implements IVentaService {

    @Autowired
    IVentasRepository ventasRepository;

    @Override
    public String guardarVenta(VentasModel venta) {
        ventasRepository.save(venta);
        return "La venta con el id: " + venta.getId() + " fue creada exitosamente";
    }
    @Override
    public List<VentasModel> listarVentas() {
        return ventasRepository.findAll();
    }

    @Override
    public Optional<VentasModel> obtenerVentaPorId(int ventaId) {
        return ventasRepository.findById(ventaId);
    }

    @Override
    public String eliminarVentaPorId(int ventaId) {
        ventasRepository.deleteById(ventaId);
        return "La venta con el id: "+ventaId+" fue eliminada exitosamente";
    }

}
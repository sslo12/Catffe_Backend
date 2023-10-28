package com.catffe.apiweb.service;

import com.catffe.apiweb.model.VentasModel;
import com.catffe.apiweb.repository.IVentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImp implements IVentasService {

    @Autowired
    IVentasRepository ventasRepository;

    @Override
    public String insertVenta(VentasModel venta) {
        ventasRepository.save(venta);
        return "La venta con el id: " + venta.getId() + " fue creada exitosamente";
    }

    @Override
    public List<VentasModel> listarVentas() {return ventasRepository.findAll();
    }
    @Override
    public List<VentasModel> obtenerVentasPorTrabajador(int trabajadorId) {
        return ventasRepository.findByTrabajadorIdOrderByFechaHoraAsc(trabajadorId);
    }

    @Override
    public Optional<VentasModel> obtenerVentaPorId(int ventaID) {
        return ventasRepository.findById(ventaID);
    }


    @Override
    public String eliminarVentaPorId(int ventaID) {
        ventasRepository.deleteById(ventaID);
        return "La mascota con el id: "+ventaID+" fue eliminada exitosamente";
    }

}




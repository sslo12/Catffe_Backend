package com.catffe.apiweb.service;

import com.catffe.apiweb.model.VentasModel;

import java.util.List;
import java.util.Optional;
public interface IVentaService {
    String crearVenta(VentasModel ventas);
    List<VentasModel> listarVentas();

    Optional<VentasModel> obtenerVentaPorId(int ventasId);

    String eliminarVentaPorId(int ventasId);

}

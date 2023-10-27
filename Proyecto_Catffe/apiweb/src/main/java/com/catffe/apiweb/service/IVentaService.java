package com.catffe.apiweb.service;

import com.catffe.apiweb.model.VentasModel;

import java.util.List;
import java.util.Optional;
public interface IVentaService {
    String guardarVenta(VentasModel venta);
    List<VentasModel> listarVentas();

    Optional<VentasModel> obtenerVentaPorId(int ventaId);

    String eliminarVentaPorId(int ventaId);

}

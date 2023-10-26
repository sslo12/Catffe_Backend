package com.catffe.apiweb.service;

import com.catffe.apiweb.model.VentaModel;
import com.catffe.apiweb.model.VentasModel;

import java.util.List;
import java.util.Optional;
public interface IVentaService {
    String crearVenta(VentaModel venta);
    List<VentasModel> listarVentas();
    Optional<VentasModel> obtenerVentaPorId(String Id);
    void eliminarVentaPorId(String Id);

}

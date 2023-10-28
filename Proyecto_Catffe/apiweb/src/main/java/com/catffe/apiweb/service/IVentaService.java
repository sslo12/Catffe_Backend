package com.catffe.apiweb.service;

import com.catffe.apiweb.model.VentasModel;

import java.util.List;
import java.util.Optional;

public interface IVentaService {
    String insertVenta(VentasModel venta);

    List<VentasModel> listarVentas();

    List<VentasModel> obtenerVentasPorTrabajador(int trabajadorId);

    Optional<VentasModel> obtenerVentaPorId(int ventaID);

    String eliminarVentaPorId(int venta);

}

package com.catffe.apiweb.service;

import com.catffe.apiweb.model.TrabajadorModel;

import java.util.List;
import java.util.Optional;

public interface ITrabajadorService {

    String guardarTrabajador(TrabajadorModel trabajador);

    List<TrabajadorModel> ListarTrabajadores();
    Optional<TrabajadorModel> obtenerTrabajadorporID(int trabajadorID);
    String eliminarTrabajadorPorID(int trabajadorID); // solo el admin puede hacer esto

    String actualizarTrabajador(TrabajadorModel Trabajador);

}

package com.catffe.apiweb.service;

import com.catffe.apiweb.model.MascotasModel;

import java.util.List;
import java.util.Optional;

public interface IMascotasService {

    String guardarMascota(MascotasModel mascota);

    List<MascotasModel> listarMascotas();

    Optional<MascotasModel> obtenerMascotaporId(int mascotaId);

    String eliminarMascotaPorId(int mascotaId);

    String actualizarMascotaPorId(MascotasModel mascota);

}

package com.catffe.apiweb.service;

import com.catffe.apiweb.model.MascotasModel;
import com.catffe.apiweb.repository.IMascotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotasServiceImp implements IMascotasService{

    @Autowired
    IMascotasRepository mascotasRepository;

    @Override
    public String guardarMascota(MascotasModel mascota) {
        mascotasRepository.save(mascota);
        return "La mascota: "+mascota.getNombre()+" fue creada exitosamente";
    }

    @Override
    public List<MascotasModel> listarMascotas() {
        return mascotasRepository.findAll();
    }

    @Override
    public Optional<MascotasModel> obtenerMascotaporId(int mascotaId) {
        return mascotasRepository.findById(mascotaId);
    }

    @Override
    public String eliminarMascotaPorId(int mascotaId) {
        mascotasRepository.deleteById(mascotaId);
        return "La mascota con el id: "+mascotaId+" fue eliminada exitosamente";
    }

    @Override
    public String actualizarMascotaPorId(MascotasModel mascota) {
        mascotasRepository.save(mascota);
        return "La mascota con el id: "+mascota.getId()+" fue actualizada exitosamente";
    }
}

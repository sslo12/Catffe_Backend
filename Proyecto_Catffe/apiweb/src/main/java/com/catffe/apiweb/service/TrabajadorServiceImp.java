package com.catffe.apiweb.service;

import com.catffe.apiweb.model.TrabajadorModel;
import com.catffe.apiweb.repository.ITrabajadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajadorServiceImp implements ITrabajadorService {
    @Autowired
    ITrabajadorRepository TrabajadorRepository;
    @Override
    public String guardarTrabajador(TrabajadorModel trabajador) {
        TrabajadorRepository.save(trabajador);
        return "El cliente con el id: "+trabajador.getId()+" fue creado exitosamente";
    }

    @Override
    public List<TrabajadorModel> ListarTrabajadores() {return TrabajadorRepository.findAll();}

    
    @Override
    public Optional<TrabajadorModel> obtenerTrabajadorporID(int trabajadorID) {
        return TrabajadorRepository.findById(trabajadorID);
    }

    @Override
    public String eliminarTrabajadorPorID(int trabajadorID) {
        TrabajadorRepository.deleteById(trabajadorID);
        return "El trabajador con el id: "+trabajadorID+" fue eliminado exitosamente";
    }

    @Override
    public String actualizarTrabajador(TrabajadorModel Trabajador) {
        TrabajadorRepository.save(Trabajador);
        return "El trabajador con el id: "+Trabajador.getId()+" fue actualizado exitosamente";
    }
}

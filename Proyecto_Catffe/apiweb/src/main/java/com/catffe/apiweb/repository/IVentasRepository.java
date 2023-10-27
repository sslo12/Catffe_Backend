package com.catffe.apiweb.repository;

import com.catffe.apiweb.model.VentasModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IVentasRepository extends MongoRepository<VentasModel, Integer> {
}

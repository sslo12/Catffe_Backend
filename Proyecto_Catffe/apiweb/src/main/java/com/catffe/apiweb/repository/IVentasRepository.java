package com.catffe.apiweb.repository;

import com.catffe.apiweb.model.VentasModel;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface IVentasRepository extends MongoRepository<VentasModel, Integer> {
}

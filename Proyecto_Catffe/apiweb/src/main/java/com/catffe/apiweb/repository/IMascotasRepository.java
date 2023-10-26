package com.catffe.apiweb.repository;

import com.catffe.apiweb.model.MascotasModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMascotasRepository extends MongoRepository<MascotasModel, Integer> {
}

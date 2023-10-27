package com.catffe.apiweb.repository;

import com.catffe.apiweb.model.TrabajadorModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITrabajadorRepository extends MongoRepository<TrabajadorModel, Integer> {
}

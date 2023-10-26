package com.catffe.apiweb.repository;

import com.catffe.apiweb.model.ClientesModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IClientesRepository extends MongoRepository<ClientesModel, Integer> {
}

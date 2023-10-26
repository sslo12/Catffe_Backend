package com.catffe.apiweb.repository;

import com.catffe.apiweb.model.ClientesModel;
import com.catffe.apiweb.model.ProductosModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IProductosRepository extends MongoRepository<ProductosModel, Integer> {

}

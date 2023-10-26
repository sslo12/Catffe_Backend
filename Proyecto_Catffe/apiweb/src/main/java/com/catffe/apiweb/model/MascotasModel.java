package com.catffe.apiweb.model;


import com.mongodb.DBObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("mascota")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MascotasModel {

    @Id
    private int id;
    private String nombre;
    private String color;
    private String raza;
    private Date fechaNac;
    private int cliente_id;
    private String especie;
}

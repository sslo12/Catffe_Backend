package com.catffe.apiweb.model;


import com.mongodb.DBObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClientesModel {

    @Id
    private int id;
    private int identificacion;
    private String nombre;
    private String apellido;
    private String correo;
    private int telefono;
    private Date fechaNacimiento;
    private List<DBObject> mascotas = new ArrayList<>();
    public void agregarMascota(DBObject nMascotas){
        mascotas.add(nMascotas);
    }

}

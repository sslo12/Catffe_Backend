package com.catffe.apiweb.model;

import com.catffe.apiweb.model.enums.TipoUsuario;
import com.mongodb.DBObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document("Trabajador")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrabajadorModel {
    @Id
    private int id;
    private String nombre;
    private String apellido;
    private Credenciales credenciales;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Credenciales {
        private String usuario;
        private int TipoUsuario;
        private String contrasena;}
}

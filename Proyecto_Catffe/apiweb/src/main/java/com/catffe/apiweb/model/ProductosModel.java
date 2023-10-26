package com.catffe.apiweb.model;

import com.catffe.apiweb.enums.tipoleche;
import com.catffe.apiweb.enums.tipo;
import com.catffe.apiweb.enums.topping;
import com.mongodb.DBObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosModel {
    @Id
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private tipo tipo;
    private int cantidadDispo;
    private tipoleche tipoLeche;
    private String origen;
    private topping topping;
    private int peso;
    private String genero;
    private String autor;
    private List<ComboProducto> combos = new ArrayList<>();
    public static class ComboProducto {
        private int productoId;
        private String nombre;
    }
}



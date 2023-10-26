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

@Document("venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaModel {

    @Id
    private String _id;
    private Date fecha_hora;
    private int cliente_id;
    private int trabajador_id;
    private List<DBObject> detalles = new ArrayList<>();
    private double precio_total;

}
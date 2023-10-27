package com.catffe.apiweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentasModel {
    @Id
    private int id_venta;
    private Date fecha_hora;
    private int cliente_id;
    private int trabajador_id;
    private double precio_total;
}

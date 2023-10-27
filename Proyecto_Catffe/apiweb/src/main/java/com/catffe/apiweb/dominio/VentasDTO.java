package com.catffe.apiweb.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentasDTO {
    private int id_venta;
    private Date fecha_hora;
    private int cliente_id;
    private int trabajador_id;
    private double precio_total;
    private List<Map<String, Integer>> detalles;
}


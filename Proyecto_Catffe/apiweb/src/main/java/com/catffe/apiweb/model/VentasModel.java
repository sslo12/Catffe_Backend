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
    private String id;
    private Date fechaHora;
    private int clienteId;
    @JsonProperty("trabajadorId")
    private int trabajadorId;
    private double precio_total;
    private List<DetalleVenta> detalles;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static
    class DetalleVenta {
        private int id_producto;
        private String nombre;
        private int cantidad;
    }
}

package com.rebolucion.app.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UNIDADES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descripcion;
    private String nombre;
    private String video;
    @ManyToOne
    @JoinColumn(name= "modulo_id", nullable = false)
    private Modulo modulo;

}

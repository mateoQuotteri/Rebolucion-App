package com.rebolucion.app.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MODULOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private Integer dificultad;
    private String profesor;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name= "tema_id", nullable = false)
    private Tema tema;
}

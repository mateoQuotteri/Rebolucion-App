package com.rebolucion.app.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Entity
@Builder
@Table(name = "TEMAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String icono;
    private Boolean hecho;



}

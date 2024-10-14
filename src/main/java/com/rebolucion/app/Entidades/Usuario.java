package com.rebolucion.app.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
@Entity
@Table(name = "USUARIOS" , uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String correo;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String contra;
    private String genero;
    private String pais;
    @Enumerated(EnumType.STRING)
    Rol rol;



}

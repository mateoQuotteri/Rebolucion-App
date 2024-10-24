package com.rebolucion.app.Repositorio;


import com.rebolucion.app.Entidades.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloRepositorio extends JpaRepository<Modulo, Long> {
}

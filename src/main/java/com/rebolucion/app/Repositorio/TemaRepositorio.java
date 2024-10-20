package com.rebolucion.app.Repositorio;


import com.rebolucion.app.Entidades.Tema;
import com.rebolucion.app.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface TemaRepositorio extends JpaRepository<Tema, Long> {

}

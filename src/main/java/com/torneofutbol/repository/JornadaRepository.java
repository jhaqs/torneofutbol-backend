package com.torneofutbol.repository;
import com.torneofutbol.entity.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface JornadaRepository extends JpaRepository<Jornada, Long> {
    List<Jornada> findByCampeonatoIdOrderByNumeroAsc(Long campeonatoId);
    boolean existsByCampeonatoId(Long campeonatoId);
}

package com.torneofutbol.repository;

import com.torneofutbol.entity.InscripcionCampeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface InscripcionCampeonatoRepository extends JpaRepository<InscripcionCampeonato, Long> {
    boolean existsByCampeonatoIdAndEquipoId(Long campeonatoId, Long equipoId);

    Optional<InscripcionCampeonato> findByCampeonatoIdAndEquipoId(Long campeonatoId, Long equipoId);

    List<InscripcionCampeonato> findByCampeonatoIdOrderByIdAsc(Long campeonatoId);

    long countByCampeonatoId(Long campeonatoId);

    @Query("""
            SELECT i
            FROM InscripcionCampeonato i
            JOIN FETCH i.equipo
            JOIN FETCH i.campeonato
            WHERE i.campeonato.id = :campeonatoId
            ORDER BY i.id ASC
            """)
    List<InscripcionCampeonato> listarConEquiposPorCampeonato(
            @Param("campeonatoId") Long campeonatoId);

    @Query("""
            SELECT i
            FROM InscripcionCampeonato i
            JOIN FETCH i.campeonato
            JOIN FETCH i.equipo
            WHERE i.campeonato.id = :campeonatoId
            ORDER BY i.id ASC
            """)
    List<InscripcionCampeonato> listarConEquiposPorCampeonato2(
            @Param("campeonatoId") Long campeonatoId);
}

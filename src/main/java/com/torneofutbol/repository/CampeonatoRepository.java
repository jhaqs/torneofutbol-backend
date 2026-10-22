package com.torneofutbol.repository;
import com.torneofutbol.entity.Campeonato;
import com.torneofutbol.entity.InscripcionCampeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> { boolean existsByAnio(Integer anio);


    @Query("""
        SELECT ic
        FROM InscripcionCampeonato ic
        JOIN FETCH ic.equipo
        WHERE ic.campeonato.id = :campeonatoId
        ORDER BY ic.id ASC
        """)
    List<InscripcionCampeonato> buscarConEquiposPorCampeonatoId(
            @Param("campeonatoId") Long campeonatoId
    );
}

package com.torneofutbol.repository;
import com.torneofutbol.entity.InscripcionCampeonato;
import com.torneofutbol.entity.Partido;
import com.torneofutbol.enums.EstadoPartido;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findByJornadaIdOrderByIdAsc(Long jornadaId);
    List<Partido> findByJornadaCampeonatoIdOrderByJornadaNumeroAscIdAsc(Long campeonatoId);
    @Query("select p from Partido p where p.jornada.campeonato.id = :campeonatoId and p.estado = :estado and (p.equipo1.id = :equipoId or p.equipo2.id = :equipoId)")
    List<Partido> buscarPorCampeonatoEquipoEstado(@Param("campeonatoId") Long campeonatoId, @Param("equipoId") Long equipoId, @Param("estado") EstadoPartido estado);
    boolean existsByJornadaCampeonatoId(Long campeonatoId);
    long countByJornadaCampeonatoIdAndEstado(Long campeonatoId, EstadoPartido estado);

    @Query("""
        SELECT p
        FROM Partido p
        JOIN FETCH p.jornada
        JOIN FETCH p.equipo1
        JOIN FETCH p.equipo2
        WHERE p.id = :id
        """)
    Optional<Partido> buscarPartidoPorId(@Param("id") Long id);
}

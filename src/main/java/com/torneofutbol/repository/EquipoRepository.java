package com.torneofutbol.repository;
import com.torneofutbol.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}

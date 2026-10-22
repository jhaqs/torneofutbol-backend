package com.torneofutbol.repository;
import com.torneofutbol.entity.SorteoCampeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface SorteoCampeonatoRepository extends JpaRepository<SorteoCampeonato, Long> { Optional<SorteoCampeonato> findByCampeonatoId(Long campeonatoId); }

package com.torneofutbol.dto.response;
import java.time.LocalDate;
public record SorteoResponse(Long id, Long campeonatoId, Long equipoGanadorId, String nombreEquipoGanador, LocalDate fechaSorteo) {}

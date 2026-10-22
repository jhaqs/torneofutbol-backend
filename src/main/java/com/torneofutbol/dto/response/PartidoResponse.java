package com.torneofutbol.dto.response;

import com.torneofutbol.enums.EstadoPartido;

import java.time.LocalDate;

public record PartidoResponse(Long id, Long campeonatoId, Long jornadaId, Integer numeroJornada, LocalDate fecha, Long equipo1Id,
                              String equipo1Nombre, Long equipo2Id, String equipo2Nombre, Integer golesEquipo1,
                              Integer golesEquipo2, EstadoPartido estado, Boolean walkover) {
}

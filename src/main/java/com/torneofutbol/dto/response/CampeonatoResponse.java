package com.torneofutbol.dto.response;

import java.time.LocalDate;

public record CampeonatoResponse(Long id, String nombre, Integer anio, LocalDate fechaInicio) {
}

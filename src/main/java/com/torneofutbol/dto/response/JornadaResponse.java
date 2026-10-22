package com.torneofutbol.dto.response;

import java.time.LocalDate;

public record JornadaResponse(Long id, Integer numero, LocalDate fecha) {
}

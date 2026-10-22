package com.torneofutbol.dto.response;

import java.time.LocalDate;
import java.util.List;

public record JornadaDetalleResponse(Long id, Integer numero, LocalDate fecha, List<PartidoResponse> partidos,
                                     DescansoResponse equipoQueDescansa) {
}

package com.torneofutbol.dto.response;
import java.time.LocalDateTime;
import java.util.Map;
public record ErrorResponse(
        LocalDateTime fecha,
        int estado,
        String error,
        String mensaje,
        String ruta,
        Map<String,String> errores)
{}

package com.torneofutbol.dto.response;
public record InscripcionResponse(
        Long id,
        Long campeonatoId,
        String nombreCampeonato,
        Long equipoId,
        String nombreEquipo,
        Boolean abandonado)
{

}
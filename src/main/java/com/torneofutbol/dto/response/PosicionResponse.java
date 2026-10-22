package com.torneofutbol.dto.response;
public record PosicionResponse(Integer posicion, Long equipoId, String nombreEquipo, String escudo, int partidosJugados, int ganados, int empatados, int perdidos, int golesFavor, int golesContra, int diferenciaGoles, int puntos) {}

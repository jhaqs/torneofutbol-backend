package com.torneofutbol.service;

import com.torneofutbol.dto.response.JornadaDetalleResponse;
import com.torneofutbol.dto.response.JornadaResponse;

import java.util.List;

public interface JornadaService {
    List<JornadaResponse> listar(Long campeonatoId);

    JornadaDetalleResponse buscarConPartidos(Long jornadaId);
}

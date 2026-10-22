package com.torneofutbol.service;

import com.torneofutbol.dto.response.InscripcionResponse;

import java.util.List;

public interface InscripcionService {
    InscripcionResponse inscribir(Long campeonatoId, Long equipoId);

    List<InscripcionResponse> listarPorCampeonato(Long campeonatoId);

}

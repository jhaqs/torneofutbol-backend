package com.torneofutbol.service;

import com.torneofutbol.dto.request.GenerarFixtureRequest;
import com.torneofutbol.dto.response.JornadaResponse;

import java.util.List;

public interface FixtureService {
    List<JornadaResponse> generar(Long campeonatoId, GenerarFixtureRequest request);
}

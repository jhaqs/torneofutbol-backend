package com.torneofutbol.service;
import com.torneofutbol.dto.request.ResultadoPartidoRequest;
import com.torneofutbol.dto.response.PartidoResponse;
public interface PartidoService {
    PartidoResponse buscarPorId(Long id);
    PartidoResponse buscarPorId2(Long id);
    PartidoResponse registrarResultado(Long id, ResultadoPartidoRequest request);
    PartidoResponse corregirResultado(Long id, ResultadoPartidoRequest request);
}

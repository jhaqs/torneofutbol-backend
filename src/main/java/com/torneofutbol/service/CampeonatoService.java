package com.torneofutbol.service;
import com.torneofutbol.dto.request.CrearCampeonatoRequest;
import com.torneofutbol.dto.response.CampeonatoResponse;
import java.util.List;
public interface CampeonatoService {
    CampeonatoResponse crear(CrearCampeonatoRequest request);
    List<CampeonatoResponse> listar();
    CampeonatoResponse buscarPorId(Long id);

}

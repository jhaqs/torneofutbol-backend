package com.torneofutbol.service;
import com.torneofutbol.dto.response.PosicionResponse;
import java.util.List;
public interface ClasificacionService { List<PosicionResponse> obtenerTabla(Long campeonatoId); }

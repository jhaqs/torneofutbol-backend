package com.torneofutbol.service;
import com.torneofutbol.dto.request.SorteoRequest;
import com.torneofutbol.dto.response.SorteoResponse;
public interface SorteoService { SorteoResponse registrar(Long campeonatoId, SorteoRequest request); SorteoResponse buscar(Long campeonatoId); }

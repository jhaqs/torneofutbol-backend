package com.torneofutbol.controller;

import com.torneofutbol.dto.response.PosicionResponse;
import com.torneofutbol.service.ClasificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campeonatos/{campeonatoId}/tabla-posiciones")
@Tag(name = "Tabla de posiciones")
public class ClasificacionController {
    private final ClasificacionService service;

    public ClasificacionController(ClasificacionService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Obtener tabla de posiciones calculada")
    public List<PosicionResponse> tabla(@PathVariable Long campeonatoId) {
        return service.obtenerTabla(campeonatoId);
    }
}

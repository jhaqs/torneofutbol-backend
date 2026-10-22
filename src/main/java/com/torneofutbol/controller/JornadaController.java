package com.torneofutbol.controller;

import com.torneofutbol.dto.response.*;
import com.torneofutbol.service.JornadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Jornadas")
public class JornadaController {
    private final JornadaService service;

    public JornadaController(JornadaService service) {
        this.service = service;
    }

    @GetMapping("/api/campeonatos/{campeonatoId}/jornadas")
    @Operation(summary = "Listar jornadas")
    public List<JornadaResponse> listar(@PathVariable Long campeonatoId) {
        return service.listar(campeonatoId);
    }

    @GetMapping("/api/jornadas/{idJornada}/partidos")
    @Operation(summary = "Consultar jornada y partidos")
    public JornadaDetalleResponse detalle(@PathVariable Long idJornada) {
        return service.buscarConPartidos(idJornada);
    }
}

package com.torneofutbol.controller;

import com.torneofutbol.dto.response.InscripcionResponse;
import com.torneofutbol.service.InscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campeonatos/{campeonatoId}/inscripciones")
@Tag(name = "Inscripciones")
public class InscripcionController {
    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    @PostMapping("/equipos/{equipoId}")
    @Operation(summary = "Inscribir equipo en campeonato")
    public InscripcionResponse inscribir(@PathVariable Long campeonatoId, @PathVariable Long equipoId) {
        return service.inscribir(campeonatoId, equipoId);
    }

    @GetMapping
    @Operation(summary = "Listar equipos inscritos")
    public List<InscripcionResponse> listar(@PathVariable Long campeonatoId) {
        return service.listarPorCampeonato(campeonatoId);
    }

    //lista de equipos inscritos en un campeonato



}

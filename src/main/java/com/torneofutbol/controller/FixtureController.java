package com.torneofutbol.controller;

import com.torneofutbol.dto.request.GenerarFixtureRequest;
import com.torneofutbol.dto.response.JornadaResponse;
import com.torneofutbol.service.FixtureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campeonatos/{campeonatoId}/fixture")
@Tag(name = "Fixture")
public class FixtureController {
    private final FixtureService service;

    public FixtureController(FixtureService service) {
        this.service = service;
    }

    @PostMapping("/generar")
    @Operation(summary = "Generar fixture todos contra todos")
    public List<JornadaResponse> generar(@PathVariable Long campeonatoId, @Valid @RequestBody GenerarFixtureRequest r) {
        return service.generar(campeonatoId, r);
    }
}

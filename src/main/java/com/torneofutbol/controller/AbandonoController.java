package com.torneofutbol.controller;

import com.torneofutbol.service.AbandonoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campeonatos/{campeonatoId}/equipos/{equipoId}")
@Tag(name = "Abandono")
public class AbandonoController {
    private final AbandonoService service;

    public AbandonoController(AbandonoService service) {
        this.service = service;
    }

    @PostMapping("/abandono")
    @Operation(summary = "Registrar abandono y aplicar walkover 3-0 a partidos programados")
    public ResponseEntity<Void> abandonar(@PathVariable Long campeonatoId, @PathVariable Long equipoId) {
        service.abandonar(campeonatoId, equipoId);
        return ResponseEntity.noContent().build();
    }
}

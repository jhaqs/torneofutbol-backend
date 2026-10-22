package com.torneofutbol.controller;

import com.torneofutbol.dto.request.CrearCampeonatoRequest;
import com.torneofutbol.dto.response.CampeonatoResponse;
import com.torneofutbol.service.CampeonatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campeonatos")
@Tag(name = "Campeonatos")
public class CampeonatoController {
    private final CampeonatoService service;

    public CampeonatoController(CampeonatoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Crear campeonato")
    public ResponseEntity<CampeonatoResponse> crear(@Valid @RequestBody CrearCampeonatoRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(r));
    }

    @GetMapping
    @Operation(summary = "Listar campeonatos")
    public List<CampeonatoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar campeonato")
    public CampeonatoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}

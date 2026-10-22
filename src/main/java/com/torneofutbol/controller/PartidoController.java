package com.torneofutbol.controller;

import com.torneofutbol.dto.request.ResultadoPartidoRequest;
import com.torneofutbol.dto.response.PartidoResponse;
import com.torneofutbol.service.PartidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidos")
@Tag(name = "Partidos")
public class PartidoController {
    private final PartidoService service;

    public PartidoController(PartidoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar partido")
    public PartidoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
        //return service.buscarPorId2(id);

    }

    @PostMapping("/{id}/resultado")
    @Operation(summary = "Registrar resultado")
    public PartidoResponse registrar(@PathVariable Long id, @Valid @RequestBody ResultadoPartidoRequest r) {
        return service.registrarResultado(id, r);
    }

    @PutMapping("/{id}/resultado")
    @Operation(summary = "Corregir resultado de partido jugado")
    public PartidoResponse corregir(@PathVariable Long id, @Valid @RequestBody ResultadoPartidoRequest r) {
        return service.corregirResultado(id, r);
    }


}

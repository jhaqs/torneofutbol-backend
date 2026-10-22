package com.torneofutbol.controller;
import com.torneofutbol.dto.request.SorteoRequest;
import com.torneofutbol.dto.response.SorteoResponse;
import com.torneofutbol.service.SorteoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/campeonatos/{campeonatoId}/sorteo") @Tag(name="Sorteo")
public class SorteoController {
    private final SorteoService service; public SorteoController(SorteoService service){this.service=service;}
    @PostMapping @Operation(summary="Registrar ganador del sorteo final") public SorteoResponse registrar(@PathVariable Long campeonatoId,@Valid @RequestBody SorteoRequest r){return service.registrar(campeonatoId,r);}
    @GetMapping @Operation(summary="Consultar sorteo final") public SorteoResponse buscar(@PathVariable Long campeonatoId){return service.buscar(campeonatoId);}
}

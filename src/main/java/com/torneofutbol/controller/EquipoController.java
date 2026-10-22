package com.torneofutbol.controller;
import com.torneofutbol.dto.request.*;
import com.torneofutbol.dto.response.EquipoResponse;
import com.torneofutbol.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@RestController @RequestMapping("/api/equipos") @Tag(name="Equipos")
public class EquipoController {
    private final EquipoService service;
    public EquipoController(EquipoService service){
        this.service=service;
    }

    @PostMapping @Operation(summary="Crear equipo")
    public ResponseEntity<EquipoResponse> crear(@Valid @RequestBody CrearEquipoRequest r){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(r));
    }

    @GetMapping @Operation(summary="Listar equipos") public List<EquipoResponse> listar(){return service.listar();}
    @GetMapping("/{id}") @Operation(summary="Consultar equipo")
    public EquipoResponse buscar(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}") @Operation(summary="Actualizar equipo")
    public EquipoResponse actualizar(@PathVariable Long id,@Valid @RequestBody ActualizarEquipoRequest r){
        return service.actualizar(id,r);
    }
    @PostMapping(value="/{id}/escudo", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="Subir o reemplazar escudo")
    public EquipoResponse escudo(@PathVariable Long id,@RequestPart("archivo") MultipartFile archivo){
        return service.guardarEscudo(id,archivo);
    }
}

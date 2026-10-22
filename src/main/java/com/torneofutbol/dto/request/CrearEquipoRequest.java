package com.torneofutbol.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record CrearEquipoRequest(@NotBlank @Size(max = 100) String nombre) {}

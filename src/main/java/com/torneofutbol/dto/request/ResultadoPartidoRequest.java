package com.torneofutbol.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
public record ResultadoPartidoRequest(@NotNull @Min(0) Integer golesEquipo1, @NotNull @Min(0) Integer golesEquipo2) {}

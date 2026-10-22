package com.torneofutbol.dto.request;
import jakarta.validation.constraints.NotNull;
public record SorteoRequest(@NotNull Long equipoGanadorId) {}

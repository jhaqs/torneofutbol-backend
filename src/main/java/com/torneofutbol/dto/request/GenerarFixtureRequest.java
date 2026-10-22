package com.torneofutbol.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record GenerarFixtureRequest(@NotNull LocalDate fechaInicio) {

}

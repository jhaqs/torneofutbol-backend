package com.torneofutbol.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CrearCampeonatoRequest(
        @NotBlank @Size(max = 150) String nombre,
        @NotNull @Min(2000) Integer anio,
        @NotNull LocalDate fechaInicio
) {}

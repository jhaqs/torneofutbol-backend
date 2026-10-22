package com.torneofutbol.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
//@Table(name = "campeonato", uniqueConstraints = @UniqueConstraint(name = "uk_campeonato_anio", columnNames = "anio"))
@Table(name = "campeonato")
public class Campeonato {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150)
    private String nombre;
    @Column(nullable = false)
    private Integer anio;
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Integer getAnio() { return anio; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
}

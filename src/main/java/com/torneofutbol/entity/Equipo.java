package com.torneofutbol.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "equipo")
public class Equipo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(length = 255)
    private String escudo;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEscudo() { return escudo; }
    public Boolean getActivo() { return activo; }
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEscudo(String escudo) { this.escudo = escudo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}

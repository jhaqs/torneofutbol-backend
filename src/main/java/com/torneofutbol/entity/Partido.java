package com.torneofutbol.entity;

import com.torneofutbol.enums.EstadoPartido;
import jakarta.persistence.*;

@Entity
@Table(name = "partido")
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "jornada_id", nullable = false)
    private Jornada jornada;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "equipo1_id", nullable = false)
    private Equipo equipo1;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "equipo2_id", nullable = false)
    private Equipo equipo2;
    @Column(name = "goles_equipo1")
    private Integer golesEquipo1;
    @Column(name = "goles_equipo2")
    private Integer golesEquipo2;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPartido estado = EstadoPartido.PROGRAMADO;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean walkover = false;

    public Long getId() {
        return id;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public Integer getGolesEquipo1() {
        return golesEquipo1;
    }

    public Integer getGolesEquipo2() {
        return golesEquipo2;
    }

    public EstadoPartido getEstado() {
        return estado;
    }

    public Boolean getWalkover() {
        return walkover;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    public void setGolesEquipo1(Integer golesEquipo1) {
        this.golesEquipo1 = golesEquipo1;
    }

    public void setGolesEquipo2(Integer golesEquipo2) {
        this.golesEquipo2 = golesEquipo2;
    }

    public void setEstado(EstadoPartido estado) {
        this.estado = estado;
    }

    public void setWalkover(Boolean walkover) {
        this.walkover = walkover;
    }
}

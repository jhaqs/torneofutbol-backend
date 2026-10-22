package com.torneofutbol.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inscripcion_campeonato", uniqueConstraints = @UniqueConstraint(name = "uk_campeonato_equipo", columnNames = {"campeonato_id", "equipo_id"}))
public class InscripcionCampeonato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campeonato_id", nullable = false)
    private Campeonato campeonato;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean abandonado = false;

    public Long getId() {
        return id;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public Boolean getAbandonado() {
        return abandonado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public void setAbandonado(Boolean abandonado) {
        this.abandonado = abandonado;
    }
}

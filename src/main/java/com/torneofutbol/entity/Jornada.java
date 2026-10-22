package com.torneofutbol.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "jornada", uniqueConstraints = {
        @UniqueConstraint(name = "uk_campeonato_jornada_numero", columnNames = {"campeonato_id", "numero"}),
        @UniqueConstraint(name = "uk_campeonato_jornada_fecha", columnNames = {"campeonato_id", "fecha"})
})
public class Jornada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campeonato_id", nullable = false)
    private Campeonato campeonato;
    @Column(nullable = false)
    private Integer numero;
    @Column(nullable = false)
    private LocalDate fecha;

    public Long getId() {
        return id;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public Integer getNumero() {
        return numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}

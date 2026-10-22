package com.torneofutbol.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sorteo_campeonato", uniqueConstraints = @UniqueConstraint(name = "uk_sorteo_campeonato", columnNames = "campeonato_id"))
public class SorteoCampeonato {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campeonato_id", nullable = false, unique = true)
    private Campeonato campeonato;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipo_ganador_id", nullable = false)
    private Equipo equipoGanador;
    @Column(name = "fecha_sorteo", nullable = false)
    private LocalDate fechaSorteo;

    public Long getId() { return id; }
    public Campeonato getCampeonato() { return campeonato; }
    public Equipo getEquipoGanador() { return equipoGanador; }
    public LocalDate getFechaSorteo() { return fechaSorteo; }
    public void setId(Long id) { this.id = id; }
    public void setCampeonato(Campeonato campeonato) { this.campeonato = campeonato; }
    public void setEquipoGanador(Equipo equipoGanador) { this.equipoGanador = equipoGanador; }
    public void setFechaSorteo(LocalDate fechaSorteo) { this.fechaSorteo = fechaSorteo; }
}

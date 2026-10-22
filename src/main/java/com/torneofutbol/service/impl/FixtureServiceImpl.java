package com.torneofutbol.service.impl;

import com.torneofutbol.dto.request.GenerarFixtureRequest;
import com.torneofutbol.dto.response.JornadaResponse;
import com.torneofutbol.entity.*;
import com.torneofutbol.enums.EstadoPartido;
import com.torneofutbol.exception.ReglaNegocioException;
import com.torneofutbol.repository.*;
import com.torneofutbol.service.FixtureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class FixtureServiceImpl implements FixtureService {
    private final CampeonatoServiceImpl campeonatos;
    private final InscripcionCampeonatoRepository inscripciones;
    private final JornadaRepository jornadas;
    private final PartidoRepository partidos;

    public FixtureServiceImpl(CampeonatoServiceImpl c, InscripcionCampeonatoRepository i, JornadaRepository j, PartidoRepository p) {
        campeonatos = c;
        inscripciones = i;
        jornadas = j;
        partidos = p;
    }

    @Override
    @Transactional
    public List<JornadaResponse> generar(Long campeonatoId, GenerarFixtureRequest request) {
        Campeonato campeonato = campeonatos.obtener(campeonatoId);
        if (!request.fechaInicio().equals(campeonato.getFechaInicio()))
            throw new ReglaNegocioException("La fecha de inicio debe ser igual a la fecha de inicio del campeonato");
        List<InscripcionCampeonato> ins = inscripciones.findByCampeonatoIdOrderByIdAsc(campeonatoId);
        if (ins.size() < 2)
            throw new ReglaNegocioException("Se necesitan al menos 2 equipos inscritos para generar el fixture.");
        if (jornadas.existsByCampeonatoId(campeonatoId))
            throw new ReglaNegocioException("El fixture de este campeonato ya fue generado.");
        List<Equipo> equipos = ins.stream().map(InscripcionCampeonato::getEquipo).toList();

        List<Equipo> rueda = new ArrayList<>(equipos);
        if (rueda.size() % 2 != 0) rueda.add(null);
        int totalJornadas = rueda.size() - 1;
        int mitad = rueda.size() / 2;
        List<JornadaResponse> respuesta = new ArrayList<>();
        for (int n = 1; n <= totalJornadas; n++) {
            Jornada jornada = new Jornada();
            jornada.setCampeonato(campeonato);
            jornada.setNumero(n);
            jornada.setFecha(request.fechaInicio().plusWeeks(n - 1));
            jornada = jornadas.save(jornada);
            for (int i = 0; i < mitad; i++) {
                Equipo a = rueda.get(i), b = rueda.get(rueda.size() - 1 - i);
                if (a != null && b != null) {
                    Partido p = new Partido();
                    p.setJornada(jornada);
                    System.out.println("Generando partido: " + a.getNombre() + " vs " + b.getNombre());
                    p.setEquipo1(a);
                    p.setEquipo2(b);
                    p.setEstado(EstadoPartido.PROGRAMADO);
                    p.setWalkover(false);
                    partidos.save(p);
                }
            }
            respuesta.add(new JornadaResponse(jornada.getId(), jornada.getNumero(), jornada.getFecha()));
            rueda = rotar(rueda);
        }
        return respuesta;
    }

    private List<Equipo> rotar(List<Equipo> actual) {
        List<Equipo> nueva = new ArrayList<>(actual);
        Equipo fijo = nueva.get(0);
        Equipo ultimo = nueva.remove(nueva.size() - 1);
        nueva.add(1, ultimo);
        nueva.set(0, fijo);
        return nueva;
    }
}

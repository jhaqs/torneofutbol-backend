package com.torneofutbol.service.impl;

import com.torneofutbol.dto.response.*;
import com.torneofutbol.entity.Jornada;
import com.torneofutbol.entity.Partido;
import com.torneofutbol.exception.RecursoNoEncontradoException;
import com.torneofutbol.repository.*;
import com.torneofutbol.service.JornadaService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JornadaServiceImpl implements JornadaService {
    private final JornadaRepository jornadas;
    private final PartidoRepository partidos;
    private final InscripcionCampeonatoRepository inscripciones;

    public JornadaServiceImpl(JornadaRepository j, PartidoRepository p, InscripcionCampeonatoRepository i) {
        jornadas = j;
        partidos = p;
        inscripciones = i;
    }

    @Override
    public List<JornadaResponse> listar(Long campeonatoId) {
        return jornadas.findByCampeonatoIdOrderByNumeroAsc(campeonatoId).stream().map(j -> new JornadaResponse(j.getId(), j.getNumero(), j.getFecha())).toList();
    }

    @Override
    public JornadaDetalleResponse buscarConPartidos(Long id) {
        Jornada j = jornadas.findById(id).
                orElseThrow(() -> new RecursoNoEncontradoException("No existe la jornada con id " + id + "."));

        //List<com.torneofutbol.entity.Partido> entidades=partidos.findByJornadaIdOrderByIdAsc(id);
        List<Partido> partidosE = Optional.of(partidos.findByJornadaIdOrderByIdAsc(id))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existen partidos para la jornada con id " + id + "."));

        List<PartidoResponse> ps = partidosE.stream()
                .map(this::map)
                .toList();

        Set<Long> participantes = new HashSet<>();
        partidosE.forEach(p -> {
            participantes.add(p.getEquipo1().getId());
            participantes.add(p.getEquipo2().getId());
        });

        DescansoResponse descanso = inscripciones.findByCampeonatoIdOrderByIdAsc(j.getCampeonato().getId())
                .stream()
                .map(i -> i.getEquipo())
                .filter(e -> !participantes.contains(e.getId()))
                .findFirst()
                .map(e -> new DescansoResponse(e.getId(), e.getNombre(), e.getEscudo()))
                .orElse(null);
        return new JornadaDetalleResponse(j.getId(), j.getNumero(), j.getFecha(), ps, descanso);
    }

    private PartidoResponse map(Partido p) {
        return new PartidoResponse(p.getId(), p.getJornada().getCampeonato().getId(), p.getJornada().getId(), p.getJornada().getNumero(),
                p.getJornada().getFecha(), p.getEquipo1().getId(), p.getEquipo1().getNombre(),
                p.getEquipo2().getId(), p.getEquipo2().getNombre(), p.getGolesEquipo1(),
                p.getGolesEquipo2(), p.getEstado(), p.getWalkover());
    }
}

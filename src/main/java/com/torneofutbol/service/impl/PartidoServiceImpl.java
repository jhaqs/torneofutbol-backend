package com.torneofutbol.service.impl;

import com.torneofutbol.dto.request.ResultadoPartidoRequest;
import com.torneofutbol.dto.response.PartidoResponse;
import com.torneofutbol.entity.Equipo;
import com.torneofutbol.entity.Jornada;
import com.torneofutbol.entity.Partido;
import com.torneofutbol.enums.EstadoPartido;
import com.torneofutbol.exception.*;
import com.torneofutbol.repository.PartidoRepository;
import com.torneofutbol.service.PartidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PartidoServiceImpl implements PartidoService {
    private final PartidoRepository repository;

    public PartidoServiceImpl(PartidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public PartidoResponse buscarPorId(Long id) {
        return map2(obtener(id));
    }

    @Override
    public PartidoResponse buscarPorId2(Long id) {
        return map2(obtener2(id));
    }

    @Override
    @Transactional
    public PartidoResponse registrarResultado(Long id, ResultadoPartidoRequest r) {
        Partido p = obtener(id);
        if (p.getEstado() != EstadoPartido.PROGRAMADO)
            throw new ReglaNegocioException("El partido ya está jugado. Use el endpoint de corrección.");
        validarGoles(r);
        p.setGolesEquipo1(r.golesEquipo1());
        p.setGolesEquipo2(r.golesEquipo2());
        p.setEstado(EstadoPartido.JUGADO);
        p.setWalkover(false);
        return map2(repository.save(p));
    }

    @Override
    @Transactional
    public PartidoResponse corregirResultado(Long id, ResultadoPartidoRequest r) {
        Partido p = obtener(id);
        if (p.getEstado() != EstadoPartido.JUGADO)
            throw new ReglaNegocioException("Solo se puede corregir un partido que ya está jugado.");
        if (Boolean.TRUE.equals(p.getWalkover()))
            throw new ReglaNegocioException("Un partido generado por walkover no se puede corregir manualmente.");
        validarGoles(r);
        p.setGolesEquipo1(r.golesEquipo1());
        p.setGolesEquipo2(r.golesEquipo2());
        return map2(repository.save(p));
    }

    private void validarGoles(ResultadoPartidoRequest r) {
        if (r.golesEquipo1() < 0 || r.golesEquipo2() < 0)
            throw new ReglaNegocioException("Los goles no pueden ser negativos.");
    }

    public Partido obtener(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("No existe el partido con id " + id + "."));
    }

    public Partido obtener2(Long id) {
        return repository.buscarPartidoPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el partido con id " + id + "."));
    }

    public Partido obtener3(Long id) {
        Partido partido = repository.buscarPartidoPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el partido con id " + id + "."));

        Long idPartido = partido.getId();
        Jornada jornada = partido.getJornada();
        Equipo equipo1 = partido.getEquipo1();
        Equipo equipo2 = partido.getEquipo2();
        return partido;
    }
/*
    private PartidoResponse map(Partido p) {
        return new PartidoResponse(p.getId(), p.getJornada().getId(), p.getJornada().getNumero(), p.getJornada().getFecha(),
                p.getEquipo1().getId(), p.getEquipo1().getNombre(), p.getEquipo2().getId(), p.getEquipo2().getNombre(),
                p.getGolesEquipo1(), p.getGolesEquipo2(), p.getEstado(), p.getWalkover());
    }*/

    private PartidoResponse map2(Partido p) {
        return new PartidoResponse(p.getId(), p.getJornada().getCampeonato().getId(), p.getJornada().getId(), p.getJornada().getNumero(), p.getJornada().getFecha(),
                p.getEquipo1().getId(), p.getEquipo1().getNombre(), p.getEquipo2().getId(), p.getEquipo2().getNombre(),
                p.getGolesEquipo1(), p.getGolesEquipo2(), p.getEstado(), p.getWalkover());
    }

}


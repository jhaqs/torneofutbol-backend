package com.torneofutbol.service.impl;

import com.torneofutbol.dto.response.InscripcionResponse;
import com.torneofutbol.entity.*;
import com.torneofutbol.exception.*;
import com.torneofutbol.repository.*;
import com.torneofutbol.service.InscripcionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionServiceImpl implements InscripcionService {
    private final InscripcionCampeonatoRepository repo;
    private final CampeonatoServiceImpl campeonatos;
    private final EquipoServiceImpl equipos;
    private final JornadaRepository jornadas;

    public InscripcionServiceImpl(InscripcionCampeonatoRepository repo, CampeonatoServiceImpl campeonatos, EquipoServiceImpl equipos, JornadaRepository jornadas) {
        this.repo = repo;
        this.campeonatos = campeonatos;
        this.equipos = equipos;
        this.jornadas = jornadas;
    }

    @Override
    public InscripcionResponse inscribir(Long campeonatoId, Long equipoId) {
        Campeonato c = campeonatos.obtener(campeonatoId);
        Equipo e = equipos.obtener(equipoId);

        if (!Boolean.TRUE.equals(e.getActivo()))
            throw new ReglaNegocioException("El equipo está inactivo y no puede inscribirse.");
        if (jornadas.existsByCampeonatoId(campeonatoId))
            throw new ReglaNegocioException("No se pueden agregar equipos después de generar el fixture.");
        if (repo.existsByCampeonatoIdAndEquipoId(campeonatoId, equipoId))
            throw new ReglaNegocioException("El equipo ya está inscrito en este campeonato.");
        InscripcionCampeonato i = new InscripcionCampeonato();
        i.setCampeonato(c);
        i.setEquipo(e);
        i.setAbandonado(false);
        return map(repo.save(i));
    }

    /* no se puede usar findByCampeonatoIdOrderByIdAsc porque no trae los equipos, solo los ids de los equipos
    @Override public List<InscripcionResponse> listarPorCampeonato(Long campeonatoId) {
        campeonatos.obtener(campeonatoId);
        return repo.findByCampeonatoIdOrderByIdAsc(campeonatoId).stream().map(this::map).toList();
    }*/

    @Override
    public List<InscripcionResponse> listarPorCampeonato(Long campeonatoId) {
        campeonatos.obtener(campeonatoId);

        return repo.listarConEquiposPorCampeonato(campeonatoId)
                .stream()
                .map(this::map)
                .toList();
    }

    private InscripcionResponse map(InscripcionCampeonato i) {
        return new InscripcionResponse(i.getId(), i.getCampeonato().getId(), i.getCampeonato().getNombre(),
                i.getEquipo().getId(), i.getEquipo().getNombre(), i.getAbandonado());
    }

}

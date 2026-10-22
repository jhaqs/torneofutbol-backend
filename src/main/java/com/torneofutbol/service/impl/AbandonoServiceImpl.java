package com.torneofutbol.service.impl;

import com.torneofutbol.entity.*;
import com.torneofutbol.enums.EstadoPartido;
import com.torneofutbol.exception.*;
import com.torneofutbol.repository.*;
import com.torneofutbol.service.AbandonoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AbandonoServiceImpl implements AbandonoService {
    private final InscripcionCampeonatoRepository inscripciones; private final PartidoRepository partidos;
    public AbandonoServiceImpl(InscripcionCampeonatoRepository i, PartidoRepository p){inscripciones=i;partidos=p;}
    @Override @Transactional public void abandonar(Long campeonatoId, Long equipoId){
        InscripcionCampeonato i=inscripciones.findByCampeonatoIdAndEquipoId(campeonatoId,equipoId).orElseThrow(()->new RecursoNoEncontradoException("El equipo no está inscrito en este campeonato."));
        if(Boolean.TRUE.equals(i.getAbandonado())) throw new ReglaNegocioException("El equipo ya figura como abandonado.");
        if(!partidos.existsByJornadaCampeonatoId(campeonatoId)) throw new ReglaNegocioException("El campeonato todavía no tiene fixture generado.");
        for(Partido p:partidos.buscarPorCampeonatoEquipoEstado(campeonatoId,equipoId,EstadoPartido.PROGRAMADO)){
            boolean equipoEsUno=p.getEquipo1().getId().equals(equipoId);
            if(equipoEsUno){p.setGolesEquipo1(0);p.setGolesEquipo2(3);} else {p.setGolesEquipo1(3);p.setGolesEquipo2(0);}
            p.setEstado(EstadoPartido.JUGADO);p.setWalkover(true);partidos.save(p);
        }
        i.setAbandonado(true);inscripciones.save(i);
    }
}

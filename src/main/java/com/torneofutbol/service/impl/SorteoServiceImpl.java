package com.torneofutbol.service.impl;

import com.torneofutbol.dto.request.SorteoRequest;
import com.torneofutbol.dto.response.PosicionResponse;
import com.torneofutbol.dto.response.SorteoResponse;
import com.torneofutbol.entity.*;
import com.torneofutbol.exception.*;
import com.torneofutbol.repository.*;
import com.torneofutbol.service.SorteoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class SorteoServiceImpl implements SorteoService {
    private final CampeonatoServiceImpl campeonatos; private final ClasificacionServiceImpl clasificacion; private final InscripcionCampeonatoRepository inscripciones; private final SorteoCampeonatoRepository sorteos; private final PartidoRepository partidos;
    public SorteoServiceImpl(CampeonatoServiceImpl c, ClasificacionServiceImpl cl, InscripcionCampeonatoRepository i, SorteoCampeonatoRepository s, PartidoRepository p){campeonatos=c;clasificacion=cl;inscripciones=i;sorteos=s;partidos=p;}
    @Override @Transactional public SorteoResponse registrar(Long campeonatoId,SorteoRequest request){
        Campeonato c=campeonatos.obtener(campeonatoId);
        if(sorteos.findByCampeonatoId(campeonatoId).isPresent()) throw new ReglaNegocioException("Ya existe un sorteo registrado para este campeonato.");
        long partidosPendientes = partidos.countByJornadaCampeonatoIdAndEstado(campeonatoId, com.torneofutbol.enums.EstadoPartido.PROGRAMADO);
        if(partidosPendientes > 0) throw new ReglaNegocioException("No se puede realizar el sorteo mientras existan partidos programados.");
        List<PosicionResponse> tabla=clasificacion.obtenerTabla(campeonatoId);
        if(tabla.size()<2) throw new ReglaNegocioException("Se necesitan al menos dos equipos para realizar el sorteo.");
        PosicionResponse a=tabla.get(0), b=tabla.get(1);
        if(a.puntos()!=b.puntos() || a.diferenciaGoles()!=b.diferenciaGoles() || a.golesFavor()!=b.golesFavor()) throw new ReglaNegocioException("No corresponde sorteo: los dos primeros no están empatados en PTS, DIF y GF.");
        if(!request.equipoGanadorId().equals(a.equipoId()) && !request.equipoGanadorId().equals(b.equipoId())) throw new ReglaNegocioException("El ganador del sorteo debe ser uno de los dos primeros equipos.");
        Equipo ganador=inscripciones.findByCampeonatoIdAndEquipoId(campeonatoId,request.equipoGanadorId()).orElseThrow(()->new RecursoNoEncontradoException("El equipo no está inscrito en el campeonato.")).getEquipo();
        SorteoCampeonato s=new SorteoCampeonato();s.setCampeonato(c);s.setEquipoGanador(ganador);s.setFechaSorteo(LocalDate.now());return map(sorteos.save(s));
    }
    @Override public SorteoResponse buscar(Long campeonatoId){return sorteos.findByCampeonatoId(campeonatoId).map(this::map).orElseThrow(()->new RecursoNoEncontradoException("No existe un sorteo para este campeonato."));}
    private SorteoResponse map(SorteoCampeonato s){return new SorteoResponse(s.getId(),s.getCampeonato().getId(),s.getEquipoGanador().getId(),s.getEquipoGanador().getNombre(),s.getFechaSorteo());}
}

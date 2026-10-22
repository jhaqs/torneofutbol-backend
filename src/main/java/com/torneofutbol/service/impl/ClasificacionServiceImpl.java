package com.torneofutbol.service.impl;

import com.torneofutbol.dto.response.PosicionResponse;
import com.torneofutbol.entity.*;
import com.torneofutbol.enums.EstadoPartido;
import com.torneofutbol.exception.RecursoNoEncontradoException;
import com.torneofutbol.repository.*;
import com.torneofutbol.service.ClasificacionService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ClasificacionServiceImpl implements ClasificacionService {
    private final CampeonatoServiceImpl campeonatos;
    private final InscripcionCampeonatoRepository inscripciones;
    private final PartidoRepository partidos;
    public ClasificacionServiceImpl(CampeonatoServiceImpl c, InscripcionCampeonatoRepository i, PartidoRepository p){campeonatos=c;inscripciones=i;partidos=p;}
    @Override public List<PosicionResponse> obtenerTabla(Long campeonatoId){
        campeonatos.obtener(campeonatoId);
        List<Equipo> equipos=inscripciones.findByCampeonatoIdOrderByIdAsc(campeonatoId).stream().map(InscripcionCampeonato::getEquipo).toList();
        Map<Long,Estadisticas> mapa=new HashMap<>(); equipos.forEach(e->mapa.put(e.getId(),new Estadisticas(e)));
        for(Partido p:partidos.findByJornadaCampeonatoIdOrderByJornadaNumeroAscIdAsc(campeonatoId)){
            if(p.getEstado()!=EstadoPartido.JUGADO || p.getGolesEquipo1()==null || p.getGolesEquipo2()==null) continue;
            Estadisticas a=mapa.get(p.getEquipo1().getId()), b=mapa.get(p.getEquipo2().getId());
            a.j++; b.j++; a.gf+=p.getGolesEquipo1(); a.gc+=p.getGolesEquipo2(); b.gf+=p.getGolesEquipo2(); b.gc+=p.getGolesEquipo1();
            if(p.getGolesEquipo1()>p.getGolesEquipo2()){a.g++;a.pts+=3;b.p++;} else if(p.getGolesEquipo1()<p.getGolesEquipo2()){b.g++;b.pts+=3;a.p++;} else {a.e++;b.e++;a.pts++;b.pts++;}
        }
        List<Estadisticas> lista=new ArrayList<>(mapa.values());
        lista.sort(Comparator.<Estadisticas>comparingInt(e -> e.pts).reversed()
                .thenComparing(Comparator.comparingInt((Estadisticas e) -> e.gf - e.gc).reversed())
                .thenComparing(Comparator.comparingInt((Estadisticas e) -> e.gf).reversed())
                .thenComparingLong(e -> e.equipo.getId()));
        List<PosicionResponse> respuesta=new ArrayList<>(); int posicion=1;
        for(Estadisticas e:lista) respuesta.add(new PosicionResponse(posicion++,e.equipo.getId(),e.equipo.getNombre(),e.equipo.getEscudo(),e.j,e.g,e.e,e.p,e.gf,e.gc,e.gf-e.gc,e.pts));
        return respuesta;
    }
    private static class Estadisticas { final Equipo equipo; int j,g,e,p,gf,gc,pts; Estadisticas(Equipo equipo){this.equipo=equipo;} }
}

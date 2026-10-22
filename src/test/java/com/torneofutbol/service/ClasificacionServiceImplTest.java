package com.torneofutbol.service;

import com.torneofutbol.dto.response.PosicionResponse;
import com.torneofutbol.entity.*;
import com.torneofutbol.enums.EstadoPartido;
import com.torneofutbol.repository.*;
import com.torneofutbol.service.impl.CampeonatoServiceImpl;
import com.torneofutbol.service.impl.ClasificacionServiceImpl;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClasificacionServiceImplTest {
    @Test
    void debeCalcularPuntosYEstadisticas(){
        CampeonatoRepository cr=mock(CampeonatoRepository.class); InscripcionCampeonatoRepository ir=mock(InscripcionCampeonatoRepository.class); PartidoRepository pr=mock(PartidoRepository.class);
        Campeonato c=new Campeonato();c.setId(1L);when(cr.findById(1L)).thenReturn(Optional.of(c));
        Equipo a=equipo(1,"A"),b=equipo(2,"B");
        InscripcionCampeonato ia=ins(a,c), ib=ins(b,c);when(ir.findByCampeonatoIdOrderByIdAsc(1L)).thenReturn(List.of(ia,ib));
        Jornada j=new Jornada();j.setId(1L);j.setCampeonato(c);
        Partido p=new Partido();p.setId(1L);p.setJornada(j);p.setEquipo1(a);p.setEquipo2(b);p.setGolesEquipo1(2);p.setGolesEquipo2(1);p.setEstado(EstadoPartido.JUGADO);
        when(pr.findByJornadaCampeonatoIdOrderByJornadaNumeroAscIdAsc(1L)).thenReturn(List.of(p));
        ClasificacionServiceImpl s=new ClasificacionServiceImpl(new CampeonatoServiceImpl(cr),ir,pr);
        List<PosicionResponse> r=s.obtenerTabla(1L);
        assertEquals(3,r.get(0).puntos()); assertEquals(0,r.get(1).puntos());
    }
    private Equipo equipo(long id,String n){Equipo e=new Equipo();e.setId(id);e.setNombre(n);return e;}
    private InscripcionCampeonato ins(Equipo e,Campeonato c){InscripcionCampeonato i=new InscripcionCampeonato();i.setEquipo(e);i.setCampeonato(c);return i;}
}

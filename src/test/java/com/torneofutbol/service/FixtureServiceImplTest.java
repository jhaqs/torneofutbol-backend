package com.torneofutbol.service;

import com.torneofutbol.dto.request.GenerarFixtureRequest;
import com.torneofutbol.dto.response.JornadaResponse;
import com.torneofutbol.entity.Campeonato;
import com.torneofutbol.entity.Equipo;
import com.torneofutbol.entity.InscripcionCampeonato;
import com.torneofutbol.entity.Jornada;
import com.torneofutbol.repository.*;
import com.torneofutbol.service.impl.CampeonatoServiceImpl;
import com.torneofutbol.service.impl.FixtureServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FixtureServiceImplTest {
    @Test
    void debeGenerarNueveJornadasY45PartidosParaDiezEquipos() {
        CampeonatoRepository cr=mock(CampeonatoRepository.class);
        InscripcionCampeonatoRepository ir=mock(InscripcionCampeonatoRepository.class);
        JornadaRepository jr=mock(JornadaRepository.class);
        PartidoRepository pr=mock(PartidoRepository.class);

        Campeonato c=new Campeonato();

        c.setId(1L);
        c.setAnio(2026);
        c.setFechaInicio(LocalDate.of(2026,10,11));

        when(cr.findById(1L)).thenReturn(Optional.of(c)); when(jr.existsByCampeonatoId(1L)).thenReturn(false);
        List<InscripcionCampeonato> ins=new ArrayList<>();

        for(long n=1;n<=10;n++){
            Equipo e=new Equipo();
            e.setId(n);
            e.setNombre("Equipo "+n);

            InscripcionCampeonato i=new InscripcionCampeonato();
            i.setEquipo(e);
            i.setCampeonato(c);
            ins.add(i);
        }

        when(ir.findByCampeonatoIdOrderByIdAsc(1L)).thenReturn(ins);
        when(jr.save(any())).thenAnswer(inv->{
            Jornada j=inv.getArgument(0);
            j.setId((long)j.getNumero());
            return j;
            }
        );

        when(pr.save(any())).thenAnswer(inv->inv.getArgument(0));
        FixtureServiceImpl service=new FixtureServiceImpl(new CampeonatoServiceImpl(cr),ir,jr,pr);

        List<JornadaResponse> r=service.generar(1L,new GenerarFixtureRequest(LocalDate.of(2026,10,11)));

        assertEquals(9,r.size()); verify(pr,times(45)).save(any());
    }

    @Test
    void debeRechazarFechaQueNoSeaDomingo(){

        CampeonatoRepository cr=mock(CampeonatoRepository.class);
        InscripcionCampeonatoRepository ir=mock(InscripcionCampeonatoRepository.class);
        JornadaRepository jr=mock(JornadaRepository.class);
        PartidoRepository pr=mock(PartidoRepository.class);

        Campeonato c=new Campeonato();
        c.setId(1L);

        when(cr.findById(1L)).thenReturn(Optional.of(c));

        FixtureServiceImpl service=new FixtureServiceImpl(new CampeonatoServiceImpl(cr),ir,jr,pr);

        assertThrows(RuntimeException.class,()->service.generar(1L,new GenerarFixtureRequest(LocalDate.of(2026,10,12))));
    }
}

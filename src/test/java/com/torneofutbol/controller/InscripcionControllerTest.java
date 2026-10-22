package com.torneofutbol.controller;

import com.torneofutbol.dto.response.InscripcionResponse;
import com.torneofutbol.service.InscripcionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InscripcionControllerTest {

    @Mock
    private InscripcionService inscripcionService;

    @InjectMocks
    private InscripcionController inscripcionController;

    @Test
    void inscribirShouldReturnInscripcionResponseWhenValidIdsProvided() {
        Long campeonatoId = 1L;
        Long equipoId = 1L;
        InscripcionResponse expectedResponse =
                new InscripcionResponse(1L,1L,1L,"equipo 1",false);
        when(inscripcionService.inscribir(campeonatoId, equipoId)).thenReturn(expectedResponse);

        InscripcionResponse actualResponse = inscripcionController.inscribir(campeonatoId, equipoId);

        assertEquals(expectedResponse, actualResponse);
        verify(inscripcionService).inscribir(campeonatoId, equipoId);
    }

//    @Test
//    void inscribirShouldThrowExceptionWhenServiceThrowsException() {
//        Long campeonatoId = 1L;
//        Long equipoId = 2L
//        when(inscripcionService.inscribir(campeonatoId, equipoId)).thenThrow(new RuntimeException("Error"));
//
//        assertThrows(RuntimeException.class, () -> inscripcionController.inscribir(campeonatoId, equipoId));
//        verify(inscripcionService).inscribir(campeonatoId, equipoId);
//    }

//    @Test
//    void listarShouldReturnListOfInscripcionResponse2WhenValidCampeonatoIdProvided() {
//        Long campeonatoId = 1L;
//        List<InscripcionResponse2> expectedList =
//                List.of(new InscripcionResponse2(1L,
//                        1L,"campeonato 1",1L,
//                "equipo 1", false));
//        when(inscripcionService.listarPorCampeonato2(campeonatoId)).thenReturn(expectedList);
//
//        List<InscripcionResponse2> actualList = inscripcionController.listar(campeonatoId);
//
//        assertEquals(expectedList, actualList);
//        verify(inscripcionService).listarPorCampeonato2(campeonatoId);
//    }

//    @Test
//    void listarShouldReturnEmptyListWhenNoInscripcionesFound() {
//        Long campeonatoId = 1L;
//        when(inscripcionService.listarPorCampeonato2(campeonatoId)).thenReturn(List.of());
//
//        List<InscripcionResponse2> actualList = inscripcionController.listar(campeonatoId);
//
//        assertTrue(actualList.isEmpty());
//        verify(inscripcionService).listarPorCampeonato2(campeonatoId);
//    }
}

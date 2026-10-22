package com.torneofutbol.service.impl;

import com.torneofutbol.dto.request.CrearCampeonatoRequest;
import com.torneofutbol.dto.response.CampeonatoResponse;
import com.torneofutbol.entity.Campeonato;
import com.torneofutbol.exception.RecursoNoEncontradoException;
import com.torneofutbol.exception.ReglaNegocioException;
import com.torneofutbol.repository.CampeonatoRepository;
import com.torneofutbol.service.CampeonatoService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {
    private final CampeonatoRepository repository;

    public CampeonatoServiceImpl(CampeonatoRepository repository) {
        this.repository = repository;
    }

    @Override
    public CampeonatoResponse crear(CrearCampeonatoRequest r) {
        if (r.fechaInicio().getDayOfWeek() != DayOfWeek.SUNDAY) {
            throw new ReglaNegocioException("La fecha de inicio debe ser un dia domingo");
        }

        Campeonato c = new Campeonato();
        c.setNombre(r.nombre().trim());
        c.setAnio(r.anio());
        c.setFechaInicio(r.fechaInicio());
        return map(repository.save(c));
    }

    @Override
    public List<CampeonatoResponse> listar() {
        return repository.findAll().stream().map(this::map).toList();
    }

    @Override
    public CampeonatoResponse buscarPorId(Long id) {
        return map(obtener(id));
    }

    public Campeonato obtener(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("No existe el campeonato con id " + id + "."));
    }

    private CampeonatoResponse map(Campeonato c) {
        return new CampeonatoResponse(c.getId(), c.getNombre(), c.getAnio(), c.getFechaInicio());
    }
}

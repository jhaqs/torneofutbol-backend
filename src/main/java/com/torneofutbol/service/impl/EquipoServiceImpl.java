package com.torneofutbol.service.impl;

import com.torneofutbol.dto.request.*;
import com.torneofutbol.dto.response.EquipoResponse;
import com.torneofutbol.entity.Equipo;
import com.torneofutbol.exception.RecursoNoEncontradoException;
import com.torneofutbol.exception.ReglaNegocioException;
import com.torneofutbol.repository.EquipoRepository;
import com.torneofutbol.service.EquipoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class EquipoServiceImpl implements EquipoService {
    private final EquipoRepository repository;
    private final Path directorioEscudos;

    public EquipoServiceImpl(EquipoRepository repository, @Value("${almacenamiento.escudos.directorio}") String directorio) {
        this.repository = repository;
        this.directorioEscudos = Paths.get(directorio).toAbsolutePath().normalize();
    }

    @Override public EquipoResponse crear(CrearEquipoRequest r) {
        if (repository.existsByNombreIgnoreCase(r.nombre().trim())) throw new ReglaNegocioException("Ya existe un equipo con el nombre indicado.");
        Equipo e = new Equipo();
        e.setNombre(r.nombre().trim());
        e.setActivo(true);
        return map(repository.save(e));
    }

    @Override public List<EquipoResponse> listar() {
        return repository.findAll().stream().map(this::map).toList();
    }

    @Override public EquipoResponse buscarPorId(Long id) {
        return map(obtener(id));
    }

    @Override public EquipoResponse actualizar(Long id, ActualizarEquipoRequest r) {
        Equipo e = obtener(id);
        if (!e.getNombre().equalsIgnoreCase(r.nombre().trim()) && repository.existsByNombreIgnoreCase(r.nombre().trim())) throw new ReglaNegocioException("Ya existe otro equipo con ese nombre.");
        e.setNombre(r.nombre().trim());
        e.setActivo(r.activo());
        return map(repository.save(e));
    }

    @Override public EquipoResponse guardarEscudo(Long id, MultipartFile archivo) {
        Equipo e = obtener(id);
        if (archivo == null || archivo.isEmpty()) throw new ReglaNegocioException("Debe enviar un archivo de imagen.");
        String tipo = archivo.getContentType();
        if (tipo == null || !tipo.startsWith("image/")) throw new ReglaNegocioException("El archivo debe ser una imagen.");
        try {
            Files.createDirectories(directorioEscudos);
            String extension = StringUtils.getFilenameExtension(archivo.getOriginalFilename());
            String nombre = UUID.randomUUID() + (extension == null ? "" : "." + extension.toLowerCase());
            Path destino = directorioEscudos.resolve(nombre).normalize();
            if (!destino.startsWith(directorioEscudos)) throw new ReglaNegocioException("Ruta de archivo no válida.");
            Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
            e.setEscudo("/uploads/equipos/" + nombre);
            return map(repository.save(e));
        } catch (IOException ex) { throw new ReglaNegocioException("No se pudo guardar el escudo."); }
    }

    public Equipo obtener(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("No existe el equipo con id " + id + "."));
    }

    private EquipoResponse map(Equipo e) {
        return new EquipoResponse(e.getId(), e.getNombre(), e.getEscudo(), e.getActivo());
    }
}

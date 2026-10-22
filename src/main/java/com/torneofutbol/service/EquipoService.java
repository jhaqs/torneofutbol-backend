package com.torneofutbol.service;
import com.torneofutbol.dto.request.*;
import com.torneofutbol.dto.response.EquipoResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
public interface EquipoService {
    EquipoResponse crear(CrearEquipoRequest request);
    List<EquipoResponse> listar();
    EquipoResponse buscarPorId(Long id);
    EquipoResponse actualizar(Long id, ActualizarEquipoRequest request);
    EquipoResponse guardarEscudo(Long id, MultipartFile archivo);
}

package com.torneofutbol.exception;

import com.torneofutbol.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecursoNoEncontradoException.class)
    ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex, HttpServletRequest req) {
        return construir(HttpStatus.NOT_FOUND, ex.getMessage(), req, Map.of());
    }
    @ExceptionHandler(ReglaNegocioException.class)
    ResponseEntity<ErrorResponse> manejarRegla(ReglaNegocioException ex, HttpServletRequest req) {
        return construir(HttpStatus.CONFLICT, ex.getMessage(), req, Map.of());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String,String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
        return construir(HttpStatus.BAD_REQUEST, "Los datos enviados no son válidos.", req, errores);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ErrorResponse> manejarIntegridad(DataIntegrityViolationException ex, HttpServletRequest req) {
        return construir(HttpStatus.CONFLICT, "La operación viola una restricción de integridad de la base de datos.", req, Map.of());
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> manejarGenerico(Exception ex, HttpServletRequest req) {
        return construir(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error interno.", req, Map.of());
    }
    private ResponseEntity<ErrorResponse> construir(HttpStatus status, String mensaje, HttpServletRequest req, Map<String,String> errores) {
        ErrorResponse r = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(), mensaje, req.getRequestURI(), errores);
        return ResponseEntity.status(status).body(r);
    }
}

package mx.kanan_tux_backend.controller;

import mx.kanan_tux_backend.dto.CalificacionDTO;
import mx.kanan_tux_backend.entity.Calificacion;
import mx.kanan_tux_backend.service.CalificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    // Endpoint para guardar una calificación (POST)
    @PostMapping
    public ResponseEntity<Calificacion> crear(@RequestBody CalificacionDTO dto) {
        return ResponseEntity.ok(calificacionService.registrarCalificacion(dto));
    }

    // Endpoint para ver el historial de calificaciones de un usuario (GET)
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Calificacion>> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(calificacionService.listarPorUsuario(idUsuario));
    }
}
package mx.kanan_tux_backend.controller;

import mx.kanan_tux_backend.dto.ConsultaDTO;
import mx.kanan_tux_backend.dto.ConsultaEstadisticaDTO;
import mx.kanan_tux_backend.dto.PuntoEstadisticaDTO;
import mx.kanan_tux_backend.entity.Consulta;
import mx.kanan_tux_backend.service.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<Consulta> registrar(@RequestBody ConsultaDTO dto) {
        return ResponseEntity.ok(consultaService.registrarConsulta(dto));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Consulta>> historialUsuario(@PathVariable Integer id) {
        return ResponseEntity.ok(consultaService.listarConsultasPorUsuario(id));
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<List<ConsultaEstadisticaDTO>> verEstadisticas() {
        return ResponseEntity.ok(consultaService.obtenerEstadisticas());
    }

    @GetMapping("/estadisticas/puntos")
    public ResponseEntity<List<PuntoEstadisticaDTO>> verEstadisticasPuntos() {
        return ResponseEntity.ok(consultaService.obtenerEstadisticasPuntos());
    }
}
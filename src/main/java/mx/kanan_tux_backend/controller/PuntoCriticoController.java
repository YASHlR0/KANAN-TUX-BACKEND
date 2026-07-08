package mx.kanan_tux_backend.controller;

import mx.kanan_tux_backend.entity.PuntoCritico;
import mx.kanan_tux_backend.service.PuntoCriticoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/puntos-criticos")
public class PuntoCriticoController {

    private final PuntoCriticoService puntoCriticoService;

    public PuntoCriticoController(PuntoCriticoService puntoCriticoService) {
        this.puntoCriticoService = puntoCriticoService;
    }

    // 1. Endpoint para ver todos los puntos (El que nos dio el 200 OK hace rato)
    @GetMapping
    public ResponseEntity<List<PuntoCritico>> obtenerTodos() {
        return ResponseEntity.ok(puntoCriticoService.listarTodos());
    }

    // 2. NUEVO: Endpoint para subir el archivo CSV
    @PostMapping("/upload")
    public ResponseEntity<?> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "El archivo está vacío."));
        }

        try {
            puntoCriticoService.guardarCsv(file);
            return ResponseEntity.ok(Map.of("message", "¡CSV importado con éxito a la base de datos!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error procesando el CSV: " + e.getMessage()));
        }
    }
}
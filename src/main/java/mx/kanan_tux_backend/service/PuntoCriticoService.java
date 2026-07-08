package mx.kanan_tux_backend.service;

import mx.kanan_tux_backend.entity.PuntoCritico;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface PuntoCriticoService {
    List<PuntoCritico> listarTodos();
    List<PuntoCritico> buscarPorRiesgo(String nivelRiesgo);
    List<PuntoCritico> buscarPorColonia(String colonia);

    // Nuevo método para procesar el CSV
    void guardarCsv(MultipartFile file) throws Exception;
}
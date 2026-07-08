package mx.kanan_tux_backend.repository;

import mx.kanan_tux_backend.entity.PuntoCritico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuntoCriticoRepository extends JpaRepository<PuntoCritico, Integer> {

    // Spring crea automáticamente las consultas SQL para estos métodos:

    // Para filtrar en el mapa solo los puntos con riesgo "ALTO", "MEDIO", etc.
    List<PuntoCritico> findByNivelRiesgo(String nivelRiesgo);

    // Para cuando el usuario busque una colonia en el frontend
    List<PuntoCritico> findByColoniaContainingIgnoreCase(String colonia);
}
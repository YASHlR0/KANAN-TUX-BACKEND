package mx.kanan_tux_backend.repository;

import mx.kanan_tux_backend.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {

    // Método mágico de Spring Boot para buscar todas las reseñas de un usuario
    List<Calificacion> findByUsuario_IdUsuario(Integer idUsuario);

}
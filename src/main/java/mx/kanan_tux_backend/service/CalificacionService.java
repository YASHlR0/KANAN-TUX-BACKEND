package mx.kanan_tux_backend.service;

import mx.kanan_tux_backend.dto.CalificacionDTO;
import mx.kanan_tux_backend.entity.Calificacion;
import java.util.List;

public interface CalificacionService {
    // Para guardar una nueva calificación/reseña
    Calificacion registrarCalificacion(CalificacionDTO dto);

    // Por si queremos ver las calificaciones de un usuario específico
    List<Calificacion> listarPorUsuario(Integer idUsuario);
}
package mx.kanan_tux_backend.service.impl;

import mx.kanan_tux_backend.dto.CalificacionDTO;
import mx.kanan_tux_backend.entity.Calificacion;
import mx.kanan_tux_backend.entity.Usuario;
import mx.kanan_tux_backend.repository.CalificacionRepository;
import mx.kanan_tux_backend.repository.UsuarioRepository;
import mx.kanan_tux_backend.service.CalificacionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final UsuarioRepository usuarioRepository;

    // Inyección de dependencias por constructor
    public CalificacionServiceImpl(CalificacionRepository calificacionRepository,
                                   UsuarioRepository usuarioRepository) {
        this.calificacionRepository = calificacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Calificacion registrarCalificacion(CalificacionDTO dto) {
        // 1. Regla de negocio: Validar las estrellas de forma segura
        if (dto.getPuntuacion() < 1 || dto.getPuntuacion() > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5 estrellas.");
        }

        // 2. Buscar si el usuario existe en la base de datos
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getIdUsuario()));

        // 3. Mapear los datos del DTO a nuestra Entidad
        Calificacion calificacion = new Calificacion();
        calificacion.setUsuario(usuario);
        calificacion.setPuntuacion(dto.getPuntuacion());
        calificacion.setComentario(dto.getComentario());

        // 4. Guardar en PostgreSQL
        return calificacionRepository.save(calificacion);
    }

    @Override
    public List<Calificacion> listarPorUsuario(Integer idUsuario) {
        return calificacionRepository.findByUsuario_IdUsuario(idUsuario);
    }

    // 🌟 NUEVO: La lógica real que va a la BD por todas las calificaciones
    @Override
    public List<Calificacion> listarTodasLasCalificaciones() {
        return calificacionRepository.findAll();
    }
}
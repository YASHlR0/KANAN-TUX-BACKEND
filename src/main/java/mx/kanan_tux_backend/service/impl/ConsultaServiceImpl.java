package mx.kanan_tux_backend.service.impl;

import mx.kanan_tux_backend.dto.ConsultaDTO;
import mx.kanan_tux_backend.dto.ConsultaEstadisticaDTO;
import mx.kanan_tux_backend.dto.PuntoEstadisticaDTO; // <-- ¡Aquí está el nuevo import!
import mx.kanan_tux_backend.entity.Consulta;
import mx.kanan_tux_backend.entity.PuntoCritico;
import mx.kanan_tux_backend.entity.Usuario;
import mx.kanan_tux_backend.repository.ConsultaRepository;
import mx.kanan_tux_backend.repository.PuntoCriticoRepository;
import mx.kanan_tux_backend.repository.UsuarioRepository;
import mx.kanan_tux_backend.service.ConsultaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PuntoCriticoRepository puntoCriticoRepository;

    public ConsultaServiceImpl(ConsultaRepository consultaRepository,
                               UsuarioRepository usuarioRepository,
                               PuntoCriticoRepository puntoCriticoRepository) {
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
        this.puntoCriticoRepository = puntoCriticoRepository;
    }

    @Override
    public Consulta registrarConsulta(ConsultaDTO dto) {
        // 1. Buscamos al usuario y al punto para asegurarnos de que existan
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        PuntoCritico punto = puntoCriticoRepository.findById(dto.getIdPunto())
                .orElseThrow(() -> new RuntimeException("Punto Crítico no encontrado"));

        // 2. Creamos la consulta y la guardamos
        Consulta consulta = new Consulta();
        consulta.setUsuario(usuario);
        consulta.setPuntoCritico(punto);
        consulta.setFechaConsulta(LocalDateTime.now()); // Ponemos la fecha y hora exacta

        return consultaRepository.save(consulta);
    }

    @Override
    public List<Consulta> listarConsultasPorUsuario(Integer idUsuario) {
        return consultaRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Override
    public List<ConsultaEstadisticaDTO> obtenerEstadisticas() {
        return consultaRepository.obtenerEstadisticasConsultas();
    }

    @Override
    public List<PuntoEstadisticaDTO> obtenerEstadisticasPuntos() {
        return consultaRepository.obtenerEstadisticasPuntos();
    }
}
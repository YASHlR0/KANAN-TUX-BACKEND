package mx.kanan_tux_backend.service;

import mx.kanan_tux_backend.dto.ConsultaDTO;
import mx.kanan_tux_backend.dto.ConsultaEstadisticaDTO;
import mx.kanan_tux_backend.dto.PuntoEstadisticaDTO; // <-- ¡Aquí está el nuevo import!
import mx.kanan_tux_backend.entity.Consulta;
import java.util.List;

public interface ConsultaService {
    Consulta registrarConsulta(ConsultaDTO dto);
    List<Consulta> listarConsultasPorUsuario(Integer idUsuario);
    List<ConsultaEstadisticaDTO> obtenerEstadisticas();
    List<PuntoEstadisticaDTO> obtenerEstadisticasPuntos();
}
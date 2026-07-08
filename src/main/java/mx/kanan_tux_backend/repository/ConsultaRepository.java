package mx.kanan_tux_backend.repository;

import mx.kanan_tux_backend.dto.ConsultaEstadisticaDTO;
import mx.kanan_tux_backend.dto.PuntoEstadisticaDTO; // <-- El nuevo molde para el pastel
import mx.kanan_tux_backend.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

    // 1. Historial normal de un usuario
    List<Consulta> findByUsuario_IdUsuario(Integer idUsuario);

    // 2. Estadística de Barras: Cuántas consultas hizo cada usuario
    @Query("SELECT u.nombre AS nombre, CAST(COUNT(c.idConsulta) AS int) AS totalConsultas FROM Consulta c JOIN c.usuario u GROUP BY u.nombre")
    List<ConsultaEstadisticaDTO> obtenerEstadisticasConsultas();

    // 3. Estadística de Pastel: Cuántas veces se consultó cada punto crítico
    @Query("SELECT p.direccionPrincipal AS direccion, CAST(COUNT(c.idConsulta) AS int) AS totalConsultas FROM Consulta c JOIN c.puntoCritico p GROUP BY p.direccionPrincipal")
    List<PuntoEstadisticaDTO> obtenerEstadisticasPuntos();
}
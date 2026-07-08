package mx.kanan_tux_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Integer idConsulta;

    // Relación con la tabla Usuarios
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Relación con la tabla Puntos Críticos
    @ManyToOne
    @JoinColumn(name = "id_punto", nullable = false)
    private PuntoCritico puntoCritico;

    @Column(name = "fecha_consulta")
    private LocalDateTime fechaConsulta;
}
package mx.kanan_tux_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Integer idToken;

    @ManyToOne // Cambiado a ManyToOne por si un usuario pide varios tokens en el historial
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "expiracion", nullable = false)
    private LocalDateTime expiracion;

    @Column(name = "utilizado")
    private Boolean utilizado = false;

    public PasswordResetToken() {}

    public PasswordResetToken(String token, Usuario usuario, int minutosExpiracion) {
        this.token = token;
        this.usuario = usuario;
        this.expiracion = LocalDateTime.now().plusMinutes(minutosExpiracion);
        this.utilizado = false;
    }

    public boolean estaExpirado() {
        return LocalDateTime.now().isAfter(this.expiracion);
    }

    // Getters y Setters
    public Integer getIdToken() { return idToken; }
    public void setIdToken(Integer idToken) { this.idToken = idToken; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public LocalDateTime getExpiracion() { return expiracion; }
    public void setExpiracion(LocalDateTime expiracion) { this.expiracion = expiracion; }
    public Boolean getUtilizado() { return utilizado; }
    public void setUtilizado(Boolean utilizado) { this.utilizado = utilizado; }
}
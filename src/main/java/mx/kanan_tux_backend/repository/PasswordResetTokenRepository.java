package mx.kanan_tux_backend.repository;

import mx.kanan_tux_backend.entity.PasswordResetToken;
import mx.kanan_tux_backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    // Spring Boot crea la consulta SQL automáticamente con solo nombrar bien el método
    Optional<PasswordResetToken> findByToken(String token);

}
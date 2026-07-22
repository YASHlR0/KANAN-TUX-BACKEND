package mx.kanan_tux_backend.service.impl;

import mx.kanan_tux_backend.dto.ForgotPasswordDTO;
import mx.kanan_tux_backend.dto.ResetPasswordDTO;
import mx.kanan_tux_backend.entity.PasswordResetToken;
import mx.kanan_tux_backend.entity.Usuario;
import mx.kanan_tux_backend.repository.PasswordResetTokenRepository;
import mx.kanan_tux_backend.repository.UsuarioRepository;
import mx.kanan_tux_backend.service.PasswordService;
import mx.kanan_tux_backend.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public PasswordServiceImpl(UsuarioRepository usuarioRepository,
                               PasswordResetTokenRepository tokenRepository,
                               PasswordEncoder passwordEncoder,
                               EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    // 💡 SIN @Transactional AQUÍ: Guarda el token, libera la BD de inmediato y LUEGO manda el correo.
    public void solicitarRecuperacion(ForgotPasswordDTO dto) {
        Usuario usuario = usuarioRepository.findByCorreo(dto.getCorreo())
                .orElseThrow(() -> new RuntimeException("No existe cuenta asociada a este correo."));

        String tokenUnico = UUID.randomUUID().toString();
        PasswordResetToken miToken = new PasswordResetToken(tokenUnico, usuario, 15);
        tokenRepository.save(miToken); // 🔓 La conexión a la base de datos se libera AQUÍ.

        // 📧 El correo se envía con la BD totalmente libre.
        emailService.enviarCorreoRecuperacion(usuario.getCorreo(), tokenUnico);
    }

    @Override
    @Transactional // 🔒 Aquí SÍ se mantiene porque modifica contraseña y token juntos
    public void cambiarPassword(ResetPasswordDTO dto) {
        PasswordResetToken tokenDb = tokenRepository.findByToken(dto.getToken())
                .orElseThrow(() -> new RuntimeException("Token inválido o no existe."));

        if (Boolean.TRUE.equals(tokenDb.getUtilizado())) {
            throw new RuntimeException("Este token ya fue utilizado anteriormente.");
        }

        if (tokenDb.estaExpirado()) {
            throw new RuntimeException("El token ha expirado. Solicita uno nuevo.");
        }

        Usuario usuario = tokenDb.getUsuario();
        usuario.setPasswordHash(passwordEncoder.encode(dto.getNuevaPassword()));
        usuarioRepository.save(usuario);

        tokenDb.setUtilizado(true);
        tokenRepository.save(tokenDb);
    }
}
package mx.kanan_tux_backend.controller;

import mx.kanan_tux_backend.dto.AuthResponseDTO;
import mx.kanan_tux_backend.dto.ForgotPasswordDTO;
import mx.kanan_tux_backend.dto.LoginRequest;
import mx.kanan_tux_backend.dto.ResetPasswordDTO;
import mx.kanan_tux_backend.entity.Usuario;
import mx.kanan_tux_backend.repository.UsuarioRepository;
import mx.kanan_tux_backend.service.PasswordService;
import mx.kanan_tux_backend.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordService passwordService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil, PasswordService passwordService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.passwordService = passwordService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!encoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Password incorrecto");
        }

        // Extraemos el rol del usuario primero
        String nombreRol = "USER";
        if (usuario.getRol() != null && usuario.getRol().getNombre() != null) {
            nombreRol = usuario.getRol().getNombre();
        }

        // Generamos el Token JWT pasándole también el rol
        String token = jwtUtil.generateToken(usuario.getCorreo(), nombreRol);

        AuthResponseDTO response = new AuthResponseDTO(token, nombreRol, usuario.getIdUsuario());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> solicitarToken(@RequestBody ForgotPasswordDTO dto) {
        passwordService.solicitarRecuperacion(dto);
        return ResponseEntity.ok("Si el correo existe, se generó un código de recuperación en la consola.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> restablecerPassword(@RequestBody ResetPasswordDTO dto) {
        passwordService.cambiarPassword(dto);
        return ResponseEntity.ok("Contraseña actualizada con éxito. Ya puedes iniciar sesión.");
    }
}
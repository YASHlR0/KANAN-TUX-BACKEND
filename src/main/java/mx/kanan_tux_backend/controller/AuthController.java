    package mx.kanan_tux_backend.controller;

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
    public class AuthController {

        private final UsuarioRepository usuarioRepository;
        private final JwtUtil jwtUtil;
        private final PasswordService passwordService; // <-- Nuevo servicio inyectado
        private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Actualizamos tu constructor para que reciba el PasswordService
        public AuthController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil, PasswordService passwordService) {
            this.usuarioRepository = usuarioRepository;
            this.jwtUtil = jwtUtil;
            this.passwordService = passwordService;
        }

        @PostMapping("/login")
        public String login(@RequestBody LoginRequest request) {
            Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (!encoder.matches(request.getPassword(), usuario.getPasswordHash())) {
                throw new RuntimeException("Password incorrecto");
            }

            return jwtUtil.generateToken(usuario.getCorreo());
        }

        // ==========================================
        // ENDPOINTS DE RECUPERACIÓN DE CONTRASEÑA
        // ==========================================

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
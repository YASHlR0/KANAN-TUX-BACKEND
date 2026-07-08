package mx.kanan_tux_backend.controller;

import mx.kanan_tux_backend.dto.LoginRequest;
import mx.kanan_tux_backend.entity.Usuario;
import mx.kanan_tux_backend.repository.UsuarioRepository;
import mx.kanan_tux_backend.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
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
}
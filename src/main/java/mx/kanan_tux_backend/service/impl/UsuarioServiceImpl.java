package mx.kanan_tux_backend.service.impl;

import mx.kanan_tux_backend.dto.UsuarioDTO;
import mx.kanan_tux_backend.entity.Rol;
import mx.kanan_tux_backend.entity.Usuario;
import mx.kanan_tux_backend.repository.RolRepository;
import mx.kanan_tux_backend.repository.UsuarioRepository;
// ESTE IMPORT ES VITAL AQUÍ TAMBIÉN
import mx.kanan_tux_backend.service.UsuarioService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Usuario registrarUsuario(UsuarioDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());

        usuario.setPasswordHash(passwordEncoder.encode(dto.getPasswordHash()));

        usuario.setEstado("ACTIVO");
        usuario.setFechaRegistro(LocalDateTime.now());

        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado en la base de datos"));
        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
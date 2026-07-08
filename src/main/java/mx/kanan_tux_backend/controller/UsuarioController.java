package mx.kanan_tux_backend.controller;

import mx.kanan_tux_backend.dto.UsuarioDTO;
import mx.kanan_tux_backend.entity.Usuario;
// ESTE IMPORT ES EL QUE QUITA EL ERROR
import mx.kanan_tux_backend.service.UsuarioService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario registrar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.registrarUsuario(usuarioDTO);
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }

    @GetMapping("/correo")
    public Usuario buscarPorCorreo(@RequestParam String correo) {
        return usuarioService.buscarPorCorreo(correo);
    }
}
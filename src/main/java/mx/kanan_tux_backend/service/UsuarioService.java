package mx.kanan_tux_backend.service;

import mx.kanan_tux_backend.dto.UsuarioDTO;
import mx.kanan_tux_backend.entity.Usuario;
import java.util.List;

public interface UsuarioService {
    Usuario registrarUsuario(UsuarioDTO dto);
    List<Usuario> listarUsuarios();
    Usuario buscarPorId(Integer id);
    Usuario buscarPorCorreo(String correo);
}
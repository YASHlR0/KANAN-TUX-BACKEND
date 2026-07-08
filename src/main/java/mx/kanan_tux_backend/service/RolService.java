package mx.kanan_tux_backend.service;

import mx.kanan_tux_backend.entity.Rol;

import java.util.List;

public interface RolService {

    Rol crearRol(Rol rol);

    List<Rol> obtenerRoles();

    Rol buscarPorId(Integer id);
}
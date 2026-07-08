package mx.kanan_tux_backend.controller;

import mx.kanan_tux_backend.entity.Rol;
import mx.kanan_tux_backend.service.RolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @PostMapping
    public Rol crear(@RequestBody Rol rol) {
        return rolService.crearRol(rol);
    }

    @GetMapping
    public List<Rol> listar() {
        return rolService.obtenerRoles();
    }

    @GetMapping("/{id}")
    public Rol buscar(@PathVariable Integer id) {
        return rolService.buscarPorId(id);
    }
}
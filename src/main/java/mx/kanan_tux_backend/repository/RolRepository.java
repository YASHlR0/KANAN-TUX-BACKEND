package mx.kanan_tux_backend.repository;

import mx.kanan_tux_backend.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}
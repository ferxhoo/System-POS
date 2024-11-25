package system.pos.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.pos.backend.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    boolean existsByNombreRol(String nombreRol);
}

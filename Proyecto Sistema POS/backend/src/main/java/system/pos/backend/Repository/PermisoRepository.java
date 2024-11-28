package system.pos.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.pos.backend.model.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    boolean existsByModuloAndNombrePermiso(String modulo, String nombrePermiso);
}

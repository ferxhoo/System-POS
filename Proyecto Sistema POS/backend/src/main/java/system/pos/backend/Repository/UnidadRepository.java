package system.pos.backend.Repository;

import system.pos.backend.model.Unidad;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad, Long> {
    Optional<Unidad> findByNombreUnidadIgnoreCase(String nombreUnidad);
    boolean existsByNombreUnidadIgnoreCase(String nombreUnidad);
}

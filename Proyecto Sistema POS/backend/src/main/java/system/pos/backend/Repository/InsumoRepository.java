package system.pos.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.pos.backend.model.Insumo;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
    Insumo findByNombreInsumo(String nombreInsumo);
}

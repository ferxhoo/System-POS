package system.pos.backend.Repository;

import system.pos.backend.model.Proveedor;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findByNumeroDocumentoIgnoreCase(String numeroDocumento);
    boolean existsByNumeroDocumentoIgnoreCase(String numeroDocumento);
}

package system.pos.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.pos.backend.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNumeroDocumentoIgnoreCase(String numeroDocumento);
    boolean existsByNumeroDocumentoIgnoreCase(String numeroDocumento);
}
package system.pos.backend.Repository;

import system.pos.backend.model.FormaPago;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagoRepository extends JpaRepository<FormaPago, Long> {
    Optional<FormaPago> findByNombreFormaPagoIgnoreCase(String nombreFormaPago);
    boolean existsByNombreFormaPagoIgnoreCase(String nombreFormaPago);
}

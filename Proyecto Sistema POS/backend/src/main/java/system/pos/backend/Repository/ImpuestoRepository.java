package system.pos.backend.Repository;

import system.pos.backend.model.Impuesto;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpuestoRepository extends JpaRepository<Impuesto, Long> {
    boolean existsByNombreImpuestoAndTarifa(String nombreImpuesto, BigDecimal tarifa);
}

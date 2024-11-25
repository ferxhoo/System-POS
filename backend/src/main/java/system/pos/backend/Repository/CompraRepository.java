package system.pos.backend.Repository;

import org.springframework.stereotype.Repository;
import system.pos.backend.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    
}

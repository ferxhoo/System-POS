package system.pos.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.pos.backend.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}

package system.pos.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system.pos.backend.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    void deleteByInsumo_Id(Long idInsumo);
    Inventario findByInsumo_Id(Long idInsumo);
}

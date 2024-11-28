package system.pos.backend.Repository;

import system.pos.backend.model.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByTipoCategoriaAndNombreCategoria(String tipoCategoria, String nombreCategoria);
}

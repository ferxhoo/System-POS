package system.pos.backend.Repository;

import org.springframework.stereotype.Repository;

import system.pos.backend.model.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, String> {

}

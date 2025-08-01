package unpa.repository.alumnos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpa.entity.Alumnos.Ficha;
import unpa.entity.Alumnos.FichaId;

import java.util.Optional;

@Repository
public interface FichaRepository extends JpaRepository<Ficha, FichaId> {

    Optional<Ficha> findById(FichaId id);
}

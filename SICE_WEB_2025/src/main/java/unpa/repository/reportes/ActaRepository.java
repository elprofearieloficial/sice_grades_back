package unpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unpa.entity.Acta;
import unpa.entity.ActaId;

public interface ActaRepository extends JpaRepository<Acta, ActaId> {
    // Puedes agregar métodos si necesitas queries específicas más adelante
}


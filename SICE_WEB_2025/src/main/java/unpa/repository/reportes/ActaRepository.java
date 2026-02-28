package unpa.repository.reportes;

import org.springframework.data.jpa.repository.JpaRepository;
import unpa.entity.actas.Acta;
import unpa.entity.actas.ActaId;

public interface ActaRepository extends JpaRepository<Acta, ActaId> {
    // Puedes agregar métodos si necesitas queries específicas más adelante
}


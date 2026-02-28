package unpa.repository.universidad;

import org.springframework.data.jpa.repository.JpaRepository;
import unpa.entity.universidad.CicloEscolar;

public interface CicloEscolarRepository extends JpaRepository<CicloEscolar, String> {
}

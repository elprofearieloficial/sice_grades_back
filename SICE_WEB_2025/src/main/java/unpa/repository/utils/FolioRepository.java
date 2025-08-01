package unpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unpa.entity.Folio;

public interface FolioRepository extends JpaRepository<Folio, String> {
}


package unpa.repository.alumnos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unpa.entity.universidad.Carrera;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, String> {
}

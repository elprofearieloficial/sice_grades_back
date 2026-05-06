package unpa.repository.alumnos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unpa.entity.Alumnos.FotoPerfil;

@Repository
public interface FotoPerfilRepository extends JpaRepository<FotoPerfil, String> {
}

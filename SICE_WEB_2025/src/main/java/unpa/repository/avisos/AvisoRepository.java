package unpa.repository.avisos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unpa.entity.avisos.Aviso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import unpa.entity.avisos.AvisoId;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, AvisoId> {
    @Query("SELECT a FROM Aviso a WHERE a.status = true AND a.dirigir IN (2, 3) ORDER BY a.fecha DESC")
    List<Aviso> findAvisosParaAlumnos();
}



package unpa.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unpa.entity.user.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE usuarios SET Clave_Usu = SHA(:nuevoPassword) WHERE Nombre_Usu = SHA(:matricula)", nativeQuery = true)
    int actualizarPassword(@Param("matricula") String matricula, @Param("nuevoPassword") String nuevaClave);

    @Query(value = "SELECT * FROM usuarios WHERE Nombre_Usu = SHA(:matricula)", nativeQuery = true)
    Optional<Usuario> findByNombreUsuario(@Param("matricula") String matricula);

    @Query(value = "SELECT * FROM usuarios WHERE Nombre_Usu = SHA(:matricula) and Clave_Usu = SHA(:password)", nativeQuery = true)
    Optional<Usuario> findByNombreClaveUsuario(@Param("matricula") String nombre, @Param("password") String clave);

    @Query(value = """
            SELECT a.Id_Alu
            FROM alumnos a
            INNER JOIN datospersonales d
                ON d.Id_Dat = a.Id_Dat_FK
               AND d.Ano_Dat = a.Ano_Dat_FK
            INNER JOIN usuarios u
                ON u.Nombre_Usu = SHA(a.Id_Alu)
            WHERE LOWER(d.Email_Dat) = LOWER(:correo)
            LIMIT 1
            """, nativeQuery = true)
    Optional<String> findMatriculaByCorreo(@Param("correo") String correo);
}


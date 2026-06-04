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

    @Query(value = """
            SELECT d.Email_Dat
            FROM alumnos a
            INNER JOIN datospersonales d
                ON d.Id_Dat = a.Id_Dat_FK
               AND d.Ano_Dat = a.Ano_Dat_FK
            WHERE a.Id_Alu = :matricula
              AND d.Email_Dat IS NOT NULL
              AND d.Email_Dat <> ''
            LIMIT 1
            """, nativeQuery = true)
    Optional<String> findCorreoByMatricula(@Param("matricula") String matricula);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE usuarios
               SET Estado_Usu = 0,
                   Errorconectar_Usu = 0,
                   Fechabloqueo_Usu = CURDATE()
             WHERE Nombre_Usu = SHA(:matricula)
            """, nativeQuery = true)
    int bloquearCuenta(@Param("matricula") String matricula);

    @Transactional
    @Modifying
    @Query(value = """
            UPDATE usuarios
               SET Clave_Usu = SHA(:matricula),
                   Estado_Usu = 1,
                   Errorconectar_Usu = 0,
                   Fechabloqueo_Usu = CURDATE()
             WHERE Nombre_Usu = SHA(:matricula)
            """, nativeQuery = true)
    int reiniciarPasswordYDesbloquear(@Param("matricula") String matricula);

    @Query(value = """
            SELECT COUNT(1)
            FROM usuariosescolares ue
            INNER JOIN usuarios u ON u.Nombre_Usu = ue.Nombre_Usu_FK
            WHERE u.Nombre_Usu = SHA(:nombreUsuario)
            """, nativeQuery = true)
    long esUsuarioEscolar(@Param("nombreUsuario") String nombreUsuario);

    @Query(value = """
            SELECT t.Id_Tra
            FROM trabajadores t
            INNER JOIN usuariosescolares ue ON ue.Id_Tra_FK = t.Id_Tra
            INNER JOIN usuarios u ON u.Nombre_Usu = ue.Nombre_Usu_FK
            WHERE LOWER(TRIM(t.Email_Tra)) = LOWER(TRIM(:correo))
              AND t.Email_Tra IS NOT NULL
              AND TRIM(t.Email_Tra) <> ''
            LIMIT 1
            """, nativeQuery = true)
    Optional<String> findLoginServiciosByCorreo(@Param("correo") String correo);
}


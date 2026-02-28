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
}


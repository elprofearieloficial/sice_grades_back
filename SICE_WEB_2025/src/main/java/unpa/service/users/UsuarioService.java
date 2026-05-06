package unpa.service.users;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.Alumnos.FotoPerfil;
import unpa.entity.user.Usuario;
import unpa.repository.alumnos.FotoPerfilRepository;
import unpa.repository.users.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private FotoPerfilRepository fotoPerfilRepository;


    public boolean cambiarPassword(String matricula, String passwordActual, String passwordNueva) {
        return obtenerUsuario(matricula).map(usuario -> {
            // Validar con SHA (simplificado, en producción usar BCrypt o Argon2)
            String hashActual = DigestUtils.sha1Hex(passwordActual);
            System.out.println("HashActual:" + hashActual + " hash");
            System.out.println("claveusu:" + usuario.getClave() + " hash");
            System.out.println("nombre:" + usuario.getNombre() + " hash");
            System.out.println("Pregunta:" + usuario.getPregunta() + " hash");
            if (usuario.getClave().equals(hashActual)) {
                //usuario.setClave(DigestUtils.sha1Hex(passwordNueva));
                usuarioRepository.actualizarPassword(matricula,passwordNueva);
                return true;
            }
            return false;
        }).orElse(

                false
        );
    }

    public Optional<Usuario> obtenerUsuario(String matricula) {
        return usuarioRepository.findByNombreUsuario(matricula);
    }

    public Optional<Usuario> obtenerUsuario(String nombreUsuario, String claveUsuario) {
        return usuarioRepository.findByNombreClaveUsuario(nombreUsuario,claveUsuario);
    }

    public Boolean existeUsuario(String nombreUsuario, String claveUsuario) {
        return obtenerUsuario(nombreUsuario,claveUsuario).map(usuario -> {
                return true;
        }).orElse(
                false
        );
    }

    public String guardarFotoPerfilUrl(String matricula, String url) {
        FotoPerfil fotoPerfil = new FotoPerfil();
        fotoPerfil.setMatricula(matricula);
        fotoPerfil.setUrl(url);
        fotoPerfilRepository.save(fotoPerfil);
        return url;
    }

    public Optional<String> obtenerFotoPerfilUrl(String matricula) {
        return fotoPerfilRepository.findById(matricula).map(FotoPerfil::getUrl);
    }
}



package unpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import unpa.entity.AlumnoDetails;
import unpa.entity.user.Usuario;
import unpa.service.users.UsuarioService;

import java.util.Collection;
import java.util.List;

@Service
public class AlumnoDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
        System.out.println("Cargando usuario: " + matricula);
        Usuario usuario = usuarioService.obtenerUsuario(matricula).get();
        // Aquí normalmente buscarías en tu BD, por ahora usamos hardcoded:
        if (usuario!=null) {
            return new AlumnoDetails(matricula,usuario.getClave()); // Clase que implementa UserDetails
        }
        throw new UsernameNotFoundException("Usuario no encontrado");
    }


}

package unpa.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import unpa.entity.AlumnoDetails;

import java.util.Collection;
import java.util.List;

@Service
public class AlumnoDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
        System.out.println("Cargando usuario: " + matricula);
        // Aquí normalmente buscarías en tu BD, por ahora usamos hardcoded:
        if (matricula.equals("18080068")) {
            return new AlumnoDetails(matricula); // Clase que implementa UserDetails
        }
        throw new UsernameNotFoundException("Usuario no encontrado");
    }


}

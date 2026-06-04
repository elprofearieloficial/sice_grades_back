package unpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import unpa.config.TenantFilter; // 👈 IMPORTANTE: Añade el import de tu filtro

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedOrigin("http://127.0.0.1:4200");
        configuration.addAllowedOrigin("http://[::1]:4200");
        configuration.addAllowedOrigin("http://localhost:4201");
        configuration.addAllowedOrigin("http://127.0.0.1:4201");
        configuration.addAllowedOrigin("http://[::1]:4201");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 👈 Instanciamos nuestro filtro "Cadenero"
        TenantFilter tenantFilter = new TenantFilter();

        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/login", "/vm2/auth/login").permitAll()
                .requestMatchers("/auth/login-servicios", "/vm2/auth/login-servicios").permitAll()
                .requestMatchers("/auth/forgot-password", "/vm2/auth/forgot-password").permitAll()
                .requestMatchers("/auth/forgot-password-servicios", "/vm2/auth/forgot-password-servicios").permitAll()
                .requestMatchers("/auth/reset-password", "/vm2/auth/reset-password").permitAll()
                .requestMatchers(
                        HttpMethod.POST,
                        "/seguridad/reportes-acceso-no-autorizado/solicitar-codigo",
                        "/vm2/seguridad/reportes-acceso-no-autorizado/solicitar-codigo"
                ).permitAll()
                .requestMatchers(
                        HttpMethod.POST,
                        "/seguridad/reportes-acceso-no-autorizado/validar-codigo",
                        "/vm2/seguridad/reportes-acceso-no-autorizado/validar-codigo"
                ).permitAll()
                .requestMatchers("/reportes/**").permitAll()
                .requestMatchers("/usuarios/*/foto-perfil").permitAll()
                .requestMatchers("/vm2/fotos-perfil/**", "/api/fotos-perfil/**").permitAll()
                .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                // 👇 EL ORDEN MÁGICO 👇
                // 1. Agregamos el filtro JWT de siempre
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // 2. Le decimos que ponga el TenantFilter EXACTAMENTE ANTES del JWT
                .addFilterBefore(tenantFilter, JwtAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package unpa.config; // <-- Ajusta a tu paquete real

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class TenantFilter extends OncePerRequestFilter {

    // Este es el nombre del Header que Angular/Android nos van a enviar
    private static final String TENANT_HEADER = "X-Campus-ID";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Buscamos la bandera en la petición HTTP
        String tenantId = request.getHeader(TENANT_HEADER);

        // 2. Si viene la bandera, la seteamos en el contexto
        if (tenantId != null && !tenantId.trim().isEmpty()) {
            TenantContext.setCurrentTenant(tenantId);
        }

        try {
            // 3. Dejamos que la petición continúe su camino hacia el Controlador
            filterChain.doFilter(request, response);
        } finally {
            // 4. ¡CRÍTICO! Limpiamos el hilo al terminar la petición
            // Si no hacemos esto, otro usuario podría heredar esta conexión por accidente
            TenantContext.clear();
        }
    }
}
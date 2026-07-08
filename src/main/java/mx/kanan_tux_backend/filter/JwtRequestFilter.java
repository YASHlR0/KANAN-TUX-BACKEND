package mx.kanan_tux_backend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.kanan_tux_backend.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // Inyectamos nuestra herramienta del Token
    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 1. El guardia estira la mano y busca el encabezado "Authorization"
        final String authorizationHeader = request.getHeader("Authorization");

        String correo = null;
        String jwt = null;

        // 2. Si trae el token y empieza con "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Cortamos la palabra "Bearer " para quedarnos solo con el código
            try {
                // Usamos la máquina para sacar el correo del usuario
                correo = jwtUtil.extractCorreo(jwt);
            } catch (Exception e) {
                System.out.println("Token inválido o expirado: " + e.getMessage());
            }
        }

        // 3. Si el token es real y el usuario no está registrado todavía en Spring Security
        if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Creamos su "pase de entrada oficial" dentro del sistema
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(correo, null, new ArrayList<>());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // ¡Le abrimos la puerta al usuario! (Se autentica en el sistema)
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 4. Dejar que la petición continúe su camino hacia el Controlador
        chain.doFilter(request, response);
    }
}
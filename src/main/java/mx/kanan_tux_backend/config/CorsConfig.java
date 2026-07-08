package mx.kanan_tux_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 1. Permitir credenciales (esencial para tokens en las cabeceras)
        config.setAllowCredentials(true);

        // 2. Permitir cualquier origen en desarrollo (Localhosts, emulador de Android 10.0.2.2, etc.)
        config.setAllowedOriginPatterns(Collections.singletonList("*"));

        // 3. Permitir todos los métodos HTTP (GET, POST, PUT, DELETE, OPTIONS)
        config.addAllowedMethod("*");

        // 4. Permitir todas las cabeceras (Authorization, Content-Type, etc.)
        config.addAllowedHeader("*");

        // Aplicar esta configuración a todas las rutas del backend
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
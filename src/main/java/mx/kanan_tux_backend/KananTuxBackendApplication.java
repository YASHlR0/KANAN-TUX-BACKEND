package mx.kanan_tux_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync; // 👈 1. IMPORTACIÓN NUEVA

@SpringBootApplication
@EnableAsync // 👈 2. ANOTACIÓN NUEVA
public class KananTuxBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KananTuxBackendApplication.class, args);
    }

}
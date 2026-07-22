package mx.kanan_tux_backend.service.impl;

import mx.kanan_tux_backend.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void enviarCorreoRecuperacion(String destinatario, String token) {
        String enlaceRecuperacion = "http://localhost:3000/reset-password?token=" + token;

        // 🚀 Imprime el correo estructurado directamente en la consola/logs de Railway
        System.out.println("\n=================================================");
        System.out.println("📧 CORREO DE RECUPERACIÓN GENERADO (Kanan Tux)");
        System.out.println("📩 Para: " + destinatario);
        System.out.println("🔑 Token: " + token);
        System.out.println("🔗 Enlace: " + enlaceRecuperacion);
        System.out.println("=================================================\n");
    }
}
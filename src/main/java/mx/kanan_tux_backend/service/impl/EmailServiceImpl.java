package mx.kanan_tux_backend.service.impl;

import mx.kanan_tux_backend.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService package mx.kanan_tux_backend.service.impl;

import mx.kanan_tux_backend.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarCorreoRecuperacion(String destinatario, String token) {
        String enlace = "http://localhost:3000/reset-password?token=" + token;

        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(destinatario);
            mensaje.setSubject("Kanan Tux - Recuperación de Contraseña");
            mensaje.setText("Hola,\n\nHas solicitado restablecer tu contraseña en Kanan Tux.\n\n"
                    + "Tu token de recuperación es:\n" + token + "\n\n"
                    + "Usa este token en tu aplicación para restablecer tu contraseña.\n\n"
                    + "Si no solicitaste este cambio, puedes ignorar este correo.");

            // 📤 Intento de envío real a través de Gmail
            mailSender.send(mensaje);
            System.out.println("✅ Correo enviado exitosamente a: " + destinatario);

        } catch (Exception e) {
            // ⚠️ Si Railway bloquea el puerto (Timeout), la app atrapa el error y NO crashea.
            System.err.println("❌ Railway bloqueó el envío SMTP: " + e.getMessage());

            // 🚀 Plan de respaldo: Imprimir en consola para que no te quedes atorado
            System.out.println("\n=================================================");
            System.out.println("🚀 [SIMULACIÓN DE RESPALDO - KANAN TUX]");
            System.out.println("📧 CORREO DE RECUPERACIÓN GENERADO");
            System.out.println("📩 Para: " + destinatario);
            System.out.println("🔑 Token: " + token);
            System.out.println("🔗 Enlace: " + enlace);
            System.out.println("=================================================\n");
        }
    }
}
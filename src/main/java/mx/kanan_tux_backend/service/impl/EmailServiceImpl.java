package mx.kanan_tux_backend.service.impl;

import mx.kanan_tux_backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:credu.1406@gmail.com}")
    private String remitente;

    @Override
    public void enviarCorreoRecuperacion(String destinatario, String token) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom(remitente);
            mensaje.setTo(destinatario);
            mensaje.setSubject("Kanan Tux - Recuperación de Contraseña");
            mensaje.setText("Hola,\n\nHas solicitado restablecer tu contraseña en Kanan Tux.\n\n"
                    + "Tu token de recuperación es: " + token + "\n\n"
                    + "Usa este token en tu aplicación para restablecer tu contraseña.\n\n"
                    + "Si no solicitaste este cambio, puedes ignorar este correo.");

            mailSender.send(mensaje);
            System.out.println("✅ ¡Correo real enviado exitosamente vía Gmail SMTP a: " + destinatario + "!");
        } catch (Exception e) {
            System.err.println("❌ Falló el envío con Gmail SMTP: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
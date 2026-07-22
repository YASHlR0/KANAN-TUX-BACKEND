package mx.kanan_tux_backend.service.impl;

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
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Kanan Tux - Recuperación de Contraseña");
        mensaje.setText("Hola,\n\nHas solicitado restablecer tu contraseña en Kanan Tux.\n\n"
                + "Tu token de recuperación es:\n" + token + "\n\n"
                + "Usa este token en tu aplicación para restablecer tu contraseña.\n\n"
                + "Si no solicitaste este cambio, puedes ignorar este correo.");

        // 📤 Envío real a través del servidor SMTP de Gmail
        mailSender.send(mensaje);

        System.out.println("✅ Correo enviado exitosamente a: " + destinatario);
    }
}
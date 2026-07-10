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
        // Esta es la URL que tu compa del Front va a configurar
        String enlaceRecuperacion = "http://localhost:3000/reset-password?token=" + token;

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Kanan Tux - Recuperación de contraseña");
        mensaje.setText("Hola,\n\n"
                + "Hemos recibido una solicitud para restablecer tu contraseña en Kanan Tux.\n\n"
                + "Haz clic en el siguiente enlace para crear una nueva contraseña:\n"
                + enlaceRecuperacion + "\n\n"
                + "Si no solicitaste este cambio, ignora este correo.\n\n"
                + "Saludos,\nEl equipo de Kanan Tux");

        mailSender.send(mensaje);
    }
}
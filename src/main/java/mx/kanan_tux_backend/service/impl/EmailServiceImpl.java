package mx.kanan_tux_backend.service.impl;

import mx.kanan_tux_backend.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Override
    public void enviarCorreoRecuperacion(String destinatario, String token) {
        String url = "https://api.brevo.com/v3/smtp/email";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        // 📩 Remitente registrado en tu cuenta de Brevo
        Map<String, Object> sender = Map.of("name", "Kanan Tux", "email", "credu.1406@gmail.com");
        Map<String, Object> to = Map.of("email", destinatario);

        Map<String, Object> body = Map.of(
                "sender", sender,
                "to", List.of(to),
                "subject", "Kanan Tux - Recuperación de Contraseña",
                "htmlContent", "<h3>Hola,</h3><p>Has solicitado restablecer tu contraseña en Kanan Tux.</p>" +
                        "<p>Tu token de recuperación es: <b>" + token + "</b></p>" +
                        "<p>Usa este token en tu aplicación para restablecer tu contraseña.</p>" +
                        "<p>Si no solicitaste este cambio, puedes ignorar este correo.</p>"
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            System.out.println("✅ ¡Correo real enviado exitosamente por Brevo a: " + destinatario + "!");
        } catch (Exception e) {
            System.err.println("❌ Falló el envío con la API de Brevo: " + e.getMessage());
        }
    }
}
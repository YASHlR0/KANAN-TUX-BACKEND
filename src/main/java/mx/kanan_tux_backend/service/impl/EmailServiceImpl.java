package mx.kanan_tux_backend.service.impl;

import mx.kanan_tux_backend.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${RESEND_API_KEY:re_ML5zGGD3_DRXxVGr8DgYrgacyVRXtAZsw}")
    private String apiKey;

    @Override
    public void enviarCorreoRecuperacion(String destinatario, String token) {
        String url = "https://api.resend.com/emails";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "from", "Kanan Tux <onboarding@resend.dev>",
                "to", new String[]{destinatario},
                "subject", "Kanan Tux - Recuperación de Contraseña",
                "html", "<h3>Hola,</h3><p>Has solicitado restablecer tu contraseña en Kanan Tux.</p>" +
                        "<p>Tu token de recuperación es: <b>" + token + "</b></p>" +
                        "<p>Si no solicitaste este cambio, puedes ignorar este correo.</p>"
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(url, entity, String.class);
            System.out.println("✅ Correo enviado exitosamente vía Resend API a: " + destinatario);
        } catch (Exception e) {
            System.err.println("❌ Error enviando correo vía Resend: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
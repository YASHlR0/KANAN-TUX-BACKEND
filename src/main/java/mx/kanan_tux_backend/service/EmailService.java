package mx.kanan_tux_backend.service;

public interface EmailService {
    void enviarCorreoRecuperacion(String destinatario, String token);
}
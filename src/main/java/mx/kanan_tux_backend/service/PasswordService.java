package mx.kanan_tux_backend.service;

import mx.kanan_tux_backend.dto.ForgotPasswordDTO;
import mx.kanan_tux_backend.dto.ResetPasswordDTO;

public interface PasswordService {
    void solicitarRecuperacion(ForgotPasswordDTO dto);
    void cambiarPassword(ResetPasswordDTO dto);
}
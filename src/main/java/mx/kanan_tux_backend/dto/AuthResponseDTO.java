package mx.kanan_tux_backend.dto;



public class AuthResponseDTO {



    private String token;

    private String rol;

    private Integer idUsuario; // ¡Súper útil para Cristian cuando quiera guardar calificaciones!



// Constructor vacío

    public AuthResponseDTO() {}



// Constructor con parámetros

    public AuthResponseDTO(String token, String rol, Integer idUsuario) {

        this.token = token;

        this.rol = rol;

        this.idUsuario = idUsuario;

    }



// Getters y Setters

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }



    public String getRol() { return rol; }

    public void setRol(String rol) { this.rol = rol; }



    public Integer getIdUsuario() { return idUsuario; }

    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

}
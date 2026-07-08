package mx.kanan_tux_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "puntos_criticos")
public class PuntoCritico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_punto")
    private Integer idPunto;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;

    @Column(name = "direccion_principal")
    private String direccionPrincipal;

    @Column(length = 150)
    private String colonia;

    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

    @Column(length = 100)
    private String ciudad;

    @Column(name = "referencia_ubicacion")
    private String referenciaUbicacion;

    @Column(name = "nivel_riesgo", nullable = false, length = 20)
    private String nivelRiesgo;

    @Column(name = "radio_geocerca_m", nullable = false)
    private Integer radioGeocercaM;

    @Column(name = "estado_semaforo", nullable = false, length = 20)
    private String estadoSemaforo;

    // --- GETTERS Y SETTERS ---

    public Integer getIdPunto() {
        return idPunto;
    }

    public void setIdPunto(Integer idPunto) {
        this.idPunto = idPunto;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getDireccionPrincipal() {
        return direccionPrincipal;
    }

    public void setDireccionPrincipal(String direccionPrincipal) {
        this.direccionPrincipal = direccionPrincipal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getReferenciaUbicacion() {
        return referenciaUbicacion;
    }

    public void setReferenciaUbicacion(String referenciaUbicacion) {
        this.referenciaUbicacion = referenciaUbicacion;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public Integer getRadioGeocercaM() {
        return radioGeocercaM;
    }

    public void setRadioGeocercaM(Integer radioGeocercaM) {
        this.radioGeocercaM = radioGeocercaM;
    }

    public String getEstadoSemaforo() {
        return estadoSemaforo;
    }

    public void setEstadoSemaforo(String estadoSemaforo) {
        this.estadoSemaforo = estadoSemaforo;
    }
}
package mx.kanan_tux_backend.service.impl;

import mx.kanan_tux_backend.entity.PuntoCritico;
import mx.kanan_tux_backend.repository.PuntoCriticoRepository;
import mx.kanan_tux_backend.service.PuntoCriticoService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class PuntoCriticoServiceImpl implements PuntoCriticoService {

    private final PuntoCriticoRepository puntoCriticoRepository;

    // Inyección por constructor
    public PuntoCriticoServiceImpl(PuntoCriticoRepository puntoCriticoRepository) {
        this.puntoCriticoRepository = puntoCriticoRepository;
    }

    @Override
    public List<PuntoCritico> listarTodos() {
        return puntoCriticoRepository.findAll();
    }

    @Override
    public List<PuntoCritico> buscarPorRiesgo(String nivelRiesgo) {
        return puntoCriticoRepository.findByNivelRiesgo(nivelRiesgo);
    }

    @Override
    public List<PuntoCritico> buscarPorColonia(String colonia) {
        return puntoCriticoRepository.findByColoniaContainingIgnoreCase(colonia);
    }

    @Override
    public void guardarCsv(MultipartFile file) throws Exception {

        // Forma moderna (v1.10.0+) de configurar el CSV para evitar el aviso "deprecated"
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, csvFormat)) {

            List<PuntoCritico> puntos = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord record : csvRecords) {
                PuntoCritico punto = new PuntoCritico();

                // Convertimos el texto del CSV a Double para que coincida con tu Entidad
                punto.setLatitud(Double.parseDouble(record.get("latitud")));
                punto.setLongitud(Double.parseDouble(record.get("longitud")));

                punto.setDireccionPrincipal(record.get("direccion_principal"));
                punto.setColonia(record.get("colonia"));
                punto.setCodigoPostal(record.get("codigo_postal"));
                punto.setCiudad(record.get("ciudad"));
                punto.setReferenciaUbicacion(record.get("referencia_ubicacion"));

                String riesgo = record.get("nivel_riesgo");
                punto.setNivelRiesgo(riesgo != null && !riesgo.isEmpty() ? riesgo : "MEDIO");

                punto.setRadioGeocercaM(Integer.parseInt(record.get("radio_geocerca_m")));
                punto.setEstadoSemaforo(record.get("estado_semaforo"));

                puntos.add(punto);
            }

            // Guardamos todos los puntos en PostgreSQL
            puntoCriticoRepository.saveAll(puntos);
        }
    }
}
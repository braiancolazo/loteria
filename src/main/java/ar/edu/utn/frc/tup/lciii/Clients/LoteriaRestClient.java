package ar.edu.utn.frc.tup.lciii.Clients;

import ar.edu.utn.frc.tup.lciii.models.Sorteo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;

@Service

public class LoteriaRestClient {
    @Autowired
    private RestTemplate restTemplate;
    final String urlbase="http://localhost:8080/";

    @CircuitBreaker(name = "loteriaExterna", fallbackMethod = "obtenerSorteoExternoFallback")
    public Sorteo[] obtenerSorteos(String fecha) {
        if(fecha != null){
            return restTemplate.getForEntity(urlbase + "sorteos"+ "?fecha=" + fecha, Sorteo[].class).getBody();

        }else{
            return restTemplate.getForEntity(urlbase + "sorteos", Sorteo[].class).getBody();
        }

    }
    public Sorteo obtenerSorteoExternoFallback(String fecha, Throwable t) {
        // Registrar el error para diagnóstico
        System.err.println("Error al obtener información del sorteo externo: " + t.getMessage());

        // Retornar un objeto con valores por defecto
        Sorteo fallbackResponse = new Sorteo();
        fallbackResponse.setFecha(fecha);
        // Configurar otros valores por defecto según tu necesidad
        fallbackResponse.setNumerosSorteados(Collections.emptyList()); // Ejemplo de lista vacía

        return fallbackResponse;
    }
}

package ar.edu.utn.frc.tup.lciii.Clients;

import ar.edu.utn.frc.tup.lciii.models.Sorteo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class LoteriaRestClient {
    @Autowired
    private RestTemplate restTemplate;
    final String urlbase="http://localhost:8080/";


    public Sorteo[] obtenerSorteos(String fecha) {
        if(fecha != null){
            return restTemplate.getForEntity(urlbase + "sorteos"+ "?fecha=" + fecha, Sorteo[].class).getBody();

        }else{
            return restTemplate.getForEntity(urlbase + "sorteos", Sorteo[].class).getBody();
        }

    }
}

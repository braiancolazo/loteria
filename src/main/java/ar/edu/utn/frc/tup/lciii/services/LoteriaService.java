package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.common.RequestPostApuesta;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponseGetSorteo;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponsePostApuesta;
import ar.edu.utn.frc.tup.lciii.entities.TotalEntity;
import ar.edu.utn.frc.tup.lciii.models.Sorteo;
import org.apache.coyote.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service

public interface LoteriaService {
    Sorteo[] getSorteosService(String fecha);
    ResponsePostApuesta responsePostApuestaService(RequestPostApuesta request);
    ResponseGetSorteo getSorteoApuestaService(int id_sorteo);
    TotalEntity getTotalService(int id_sorteo);
}



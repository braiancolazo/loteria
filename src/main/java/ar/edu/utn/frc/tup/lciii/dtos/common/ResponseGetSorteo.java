package ar.edu.utn.frc.tup.lciii.dtos.common;

import ar.edu.utn.frc.tup.lciii.models.Apuesta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResponseGetSorteo {
    int id_sorteo;
    String fecha_sorteo;
    List<Apuesta> apuestas;
    int totalEnReserva;
}


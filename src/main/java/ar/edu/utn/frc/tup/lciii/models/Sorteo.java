package ar.edu.utn.frc.tup.lciii.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sorteo {
    int numeroSorteo;
    String fecha;
    int dineroTotalAcumulado;
    List<List<Integer>> numerosSorteados;
}

package ar.edu.utn.frc.tup.lciii.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostApuesta {
    int id_sorteo;
    String fecha_sorteo;
    String id_cliente;
    int numero;
    String resultado;
}

package ar.edu.utn.frc.tup.lciii.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Apuesta {
    String id_cliente;
    String numero;
    String resultado;
    int MontoApostado;
    BigDecimal premio;
}

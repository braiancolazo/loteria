package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class ApuestaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String fecha_sorteo;
    @Column
    String id_cliente;
    @Column
    int numero;
    @Column
    int montoApostado;
    @Column
    BigDecimal premio;
    @Column
    String resultado;



}

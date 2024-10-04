package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TotalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String fecha_sorteo;
    @Column
    int id_sorteo;
    @Column
    int totalDeApuestas;
    @Column
    BigDecimal totalPagado;
    @Column
    int totalEnReserva;
}

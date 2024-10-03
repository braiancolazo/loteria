package ar.edu.utn.frc.tup.lciii.JpaRepository;

import ar.edu.utn.frc.tup.lciii.entities.ApuestaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApuestaJpaRepository  extends JpaRepository<ApuestaEntity,Long> {
}

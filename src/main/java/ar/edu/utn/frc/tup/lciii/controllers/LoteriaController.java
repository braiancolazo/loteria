package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.JpaRepository.ApuestaJpaRepository;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestPostApuesta;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponsePostApuesta;
import ar.edu.utn.frc.tup.lciii.entities.ApuestaEntity;
import ar.edu.utn.frc.tup.lciii.models.Sorteo;
import ar.edu.utn.frc.tup.lciii.services.LoteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("loteria")
public class LoteriaController {
    @Autowired
    LoteriaService loteriaService;
    @Autowired
    ApuestaJpaRepository apuestaJpaRepository;


    @GetMapping("sorteos")
    public ResponseEntity<Sorteo[]> getSorteos(@RequestParam(required = false) String fecha ) {
        return ResponseEntity.ok(loteriaService.getSorteosService(fecha));
    }
    @GetMapping
    public List<ApuestaEntity> getapuestas() {
        return apuestaJpaRepository.findAll();
    }
    @PostMapping("apuesta")
    public ResponseEntity<ResponsePostApuesta> apuesta(@RequestBody RequestPostApuesta request) {
        return ResponseEntity.ok(loteriaService.responsePostApuestaService(request));
    }



}

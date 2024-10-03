package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.Clients.LoteriaRestClient;
import ar.edu.utn.frc.tup.lciii.JpaRepository.ApuestaJpaRepository;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestPostApuesta;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponsePostApuesta;
import ar.edu.utn.frc.tup.lciii.entities.ApuestaEntity;
import ar.edu.utn.frc.tup.lciii.models.Sorteo;
import ar.edu.utn.frc.tup.lciii.services.LoteriaService;
import jakarta.persistence.Entity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service

public class LoteriaServiceImpl implements LoteriaService {
    @Autowired
    LoteriaRestClient loteriaRestClient;
    @Autowired
    ApuestaJpaRepository apuestaJpaRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Sorteo[] getSorteosService(String fecha) {
        return loteriaRestClient.obtenerSorteos(fecha);
    }

    @Override
    public ResponsePostApuesta responsePostApuestaService(RequestPostApuesta request) {
        ApuestaEntity apuestaEntity = new ApuestaEntity();
        Sorteo[] sorteos = loteriaRestClient.obtenerSorteos(request.getFecha_sorteo().toString());
        ResponsePostApuesta response = new ResponsePostApuesta();
        response.setFecha_sorteo(request.getFecha_sorteo().toString());
        response.setId_cliente(request.getId_cliente());
        response.setId_sorteo(sorteos[0].getNumeroSorteo());
        response.setResultado("Perdedor");
        apuestaEntity.setResultado("Perdedor");
        int aciertos = 0;
        int aciertosActuales = 0;
        int numero = 0;
        for (List<Integer> lst: sorteos[0].getNumerosSorteados()){
            for (int i =4; i>-1;i--){

                if(lst.get(1).toString().charAt(i)==request.getNumero().charAt(i)){
                    aciertosActuales++;
                }
            }
            if (aciertosActuales>aciertos && aciertosActuales>1){
                aciertos = aciertosActuales;
                numero = lst.get(1);
                response.setResultado("GANADOR");
                apuestaEntity.setResultado("GANADOR");
            }
            aciertosActuales=0;
        }
        response.setNumero(numero);
        apuestaEntity.setNumero(response.getNumero());
        apuestaEntity.setFecha_sorteo(request.getFecha_sorteo().toString());
        apuestaEntity.setId_cliente(request.getId_cliente());
        apuestaEntity.setMontoApostado(request.getMontoApostado());
        if(aciertos==2){
            apuestaEntity.setPremio(new BigDecimal((700* request.getMontoApostado())/100));
        }if (aciertos==3){
            apuestaEntity.setPremio(new BigDecimal((7000* request.getMontoApostado())/100));

        }if (aciertos==4){
            apuestaEntity.setPremio(new BigDecimal((60000* request.getMontoApostado())/100));

        }if (aciertos==5){
            apuestaEntity.setPremio(new BigDecimal((350000* request.getMontoApostado())/100));
        }
        List<ApuestaEntity> apuestaEntities = apuestaJpaRepository.findAll();
        if (apuestaEntity.getPremio().compareTo(new BigDecimal(sorteos[0].getDineroTotalAcumulado())) > 0){
            int contador =0;
            for (ApuestaEntity a: apuestaEntities){
                contador++;
                if(contador % 5 ==0){
                    BigDecimal ajuste = new BigDecimal((sorteos[0].getDineroTotalAcumulado()*0.25));
                    apuestaEntity.setPremio(apuestaEntity.getPremio().subtract(ajuste));
                }
            }
        }
        apuestaJpaRepository.save(apuestaEntity);



        return response;
    }
}

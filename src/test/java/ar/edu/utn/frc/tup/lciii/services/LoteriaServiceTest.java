package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.Clients.LoteriaRestClient;
import ar.edu.utn.frc.tup.lciii.JpaRepository.ApuestaJpaRepository;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestPostApuesta;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponsePostApuesta;
import ar.edu.utn.frc.tup.lciii.entities.ApuestaEntity;
import ar.edu.utn.frc.tup.lciii.models.Sorteo;
import ar.edu.utn.frc.tup.lciii.services.impl.LoteriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoteriaServiceTest {
    @Mock
    LoteriaRestClient loteriaRestClient;
    @Mock
    ApuestaJpaRepository apuestaJpaRepository;
    @InjectMocks
    LoteriaServiceImpl loteriaService;
    @Mock
    ModelMapper modelMapper;

    private RequestPostApuesta request;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new RequestPostApuesta();
        request.setFecha_sorteo(LocalDate.of(2024, 10, 1));
        request.setId_cliente("12345");
        request.setNumero("12345");
        request.setMontoApostado(100);
    }

    @Test
    void testResponsePostApuestaService_Ganador() {
        // Mocking sorteos
        Sorteo sorteo = new Sorteo();
        sorteo.setNumeroSorteo(1);
        sorteo.setNumerosSorteados(Arrays.asList(
                Arrays.asList(0, 12345),
                Arrays.asList(1, 54321)
        ));
        sorteo.setDineroTotalAcumulado(100000);

        when(loteriaRestClient.obtenerSorteos("2024-10-01")).thenReturn(new Sorteo[]{sorteo});

        // Mocking the repository
        when(apuestaJpaRepository.findAll()).thenReturn(Collections.emptyList());

        // Call the method to test
        ResponsePostApuesta response = loteriaService.responsePostApuestaService(request);

        // Verify results
        assertEquals("GANADOR", response.getResultado());
        assertEquals(12345, response.getNumero());
        assertEquals("12345", response.getId_cliente());

        // Verify that the apuestaEntity was saved correctly
        verify(apuestaJpaRepository).save(any(ApuestaEntity.class));
    }




}

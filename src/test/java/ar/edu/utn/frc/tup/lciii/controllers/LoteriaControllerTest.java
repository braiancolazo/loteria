package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.RequestPostApuesta;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponseGetSorteo;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponsePostApuesta;
import ar.edu.utn.frc.tup.lciii.entities.TotalEntity;
import ar.edu.utn.frc.tup.lciii.models.Apuesta;
import ar.edu.utn.frc.tup.lciii.services.LoteriaService;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.mock.web.MockHttpServletResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class LoteriaControllerTest {
    @Mock
    LoteriaService loteriaService;
    @InjectMocks
    LoteriaController loteriaController;
    @Autowired
    ObjectMapper objectMapper ;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loteriaController).build();

    }
    @Test
    public void PostApuestaTest3()throws Exception {
        // Arrange
        RequestPostApuesta request = new RequestPostApuesta();
        request.setFecha_sorteo(LocalDate.of(2024,10,1));
        request.setNumero(String.valueOf(56789));
        request.setId_cliente("cliente123");
        request.setMontoApostado(100);

        // Configura el objeto request según tu necesidad

        ResponsePostApuesta expectedResponse = new ResponsePostApuesta();
        expectedResponse.setId_sorteo(1);
        expectedResponse.setFecha_sorteo("2024-10-01");
        expectedResponse.setId_cliente("cliente123");
        expectedResponse.setNumero(56789);
        expectedResponse.setResultado("ganador");
        // Configura el objeto expectedResponse según tu necesidad

        when(loteriaService.responsePostApuestaService(any(RequestPostApuesta.class))).thenReturn(expectedResponse);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/loteria/apuesta")
                        .contentType(MediaType.APPLICATION_JSON) // Asegúrate de establecer el tipo de contenido
                        .content(jsonRequest)) // Aquí se incluye el cuerpo de la solicitud
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_sorteo").value(1))
                .andExpect(jsonPath("$.fecha_sorteo").value("2024-10-01"))
                .andExpect(jsonPath("$.id_cliente").value("cliente123"))
                .andExpect(jsonPath("$.numero").value(56789)) // Ajusta aquí el valor esperado
                .andExpect(jsonPath("$.resultado").value("ganador"));
    }
    @Test
    public void GetApuestaInfoTest2() throws Exception {
        ResponseGetSorteo  response = new ResponseGetSorteo();
        response.setId_sorteo(123);

        when(loteriaService.getSorteoApuestaService(123)).thenReturn(response);

        mockMvc.perform(get("/loteria/sorteo/123")).andDo(print()).andExpect(status().isOk())
                        .andExpect(jsonPath("$.id_sorteo").value(123));



    }
    @Test
    public void GetTotalTest2()  throws Exception {
        TotalEntity response = new TotalEntity();
        response.setTotalDeApuestas(100);

        when(loteriaService.getTotalService(123)).thenReturn(response);

        mockMvc.perform(get("/loteria/total/123")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalDeApuestas").value(100));

    }
    @Test
    public void PostApuestaTest2()throws Exception {
        // Arrange
        RequestPostApuesta request = new RequestPostApuesta();
        // Configura el objeto request según tu necesidad

        ResponsePostApuesta expectedResponse = new ResponsePostApuesta();
        expectedResponse.setId_sorteo(1);
        expectedResponse.setFecha_sorteo("2024-10-01");
        expectedResponse.setId_cliente("cliente123");
        expectedResponse.setNumero(42);
        expectedResponse.setResultado("ganador");
        // Configura el objeto expectedResponse según tu necesidad

        when(loteriaService.responsePostApuestaService(any(RequestPostApuesta.class))).thenReturn(expectedResponse);
        String jsonRequest = "{"
                + "\"fecha_sorteo\":\"2024-10-01\","
                + "\"id_cliente\":\"cliente123\","
                + "\"numero\":\"42\","
                + "\"montoApostado\":100"
                + "}";

        // Act & Assert
        mockMvc.perform(post("/loteria/apuesta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id_sorteo").value(1))
                .andExpect(jsonPath("$.fecha_sorteo").value("2024-10-01"))
                .andExpect(jsonPath("$.id_cliente").value("cliente123"))
                .andExpect(jsonPath("$.numero").value(42))
                .andExpect(jsonPath("$.resultado").value("ganador"));

    }




    @Test
    public void PostApuestaTest() {
        //arranque
        ResponsePostApuesta response = new ResponsePostApuesta();
        response.setResultado("Ganador");
        response.setNumero(12345);
        response.setId_cliente("pepe");
        response.setFecha_sorteo("2020-09-25");
        response.setId_sorteo(123);
        RequestPostApuesta request = new RequestPostApuesta();

        when(loteriaService.responsePostApuestaService(request)).thenReturn(response);

        //act
        ResponseEntity<ResponsePostApuesta> responseController = loteriaController.apuesta(request);

        //aserts
        assertEquals(200, responseController.getStatusCodeValue());
        assertEquals("Ganador", responseController.getBody().getResultado());
    }
    @Test
    public void GetApuestaInfoTest() {
        ResponseGetSorteo  response = new ResponseGetSorteo();
        response.setId_sorteo(123);

        when(loteriaService.getSorteoApuestaService(123)).thenReturn(response);

        ResponseEntity<ResponseGetSorteo> responseController = loteriaController.getInfoSorteo(123);

        assertEquals(200, responseController.getStatusCodeValue());

    }
    @Test
    public void GetTotalTest() {
        TotalEntity response = new TotalEntity();
        response.setTotalDeApuestas(100);

        when(loteriaService.getTotalService(123)).thenReturn(response);

        ResponseEntity<TotalEntity> responseController =loteriaController.getTotalController(123);

        assertEquals(200, responseController.getStatusCodeValue());
        assertEquals(100, responseController.getBody().getTotalDeApuestas());
    }
    @Test
    void getSorteoInfo() throws Exception {
        Apuesta apuestaDto=new Apuesta();
        apuestaDto.setId_cliente("juan");
        apuestaDto.setMontoApostado(100);
        apuestaDto.setNumero("12345");
        apuestaDto.setPremio(new BigDecimal(3500));

        List<Apuesta> apuestaDtoList=new ArrayList<>();
        apuestaDtoList.add(apuestaDto);

        ResponseGetSorteo sorteoInfoGeneralDto=new ResponseGetSorteo();
        sorteoInfoGeneralDto.setId_sorteo(123);
        sorteoInfoGeneralDto.setApuestas(apuestaDtoList);
        sorteoInfoGeneralDto.setTotalEnReserva(996500);
        sorteoInfoGeneralDto.setFecha_sorteo("2024-01-16");

        when(loteriaService.getSorteoApuestaService(123)).thenReturn(sorteoInfoGeneralDto);

        Integer id_sorteo=123;
        this.mockMvc.perform(get("/loteria/sorteo/{id_sorteo}",id_sorteo))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id_sorteo").value(123))
                .andExpect(jsonPath("$.fecha_sorteo").value("2024-01-16"))
                .andExpect(jsonPath("$.apuestas[0].id_cliente").value("juan"))
                .andExpect(jsonPath("$.apuestas[0].numero").value("12345"))
                .andExpect(jsonPath("$.apuestas[0].montoApostado").value(100))
                .andExpect(jsonPath("$.apuestas[0].premio").value(new BigDecimal(3500)))
                .andExpect(jsonPath("$.totalEnReserva").value(996500));
    }

    @Test
    void getTotal() throws Exception{
        Integer id_sorteo=123;
        TotalEntity sorteoTotalDto=new TotalEntity();
        sorteoTotalDto.setTotalDeApuestas(1);
        sorteoTotalDto.setTotalEnReserva(996500);
        sorteoTotalDto.setTotalPagado(new BigDecimal(3500));
        sorteoTotalDto.setId_sorteo(123);
        sorteoTotalDto.setFecha_sorteo("2024-01-16");

        when(loteriaService.getTotalService(123)).thenReturn(sorteoTotalDto);

        this.mockMvc.perform(get("/loteria/total/{id_sorteo}",id_sorteo)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id_sorteo").value(123))
                .andExpect(jsonPath("$.fecha_sorteo").value("2024-01-16"))
                .andExpect(jsonPath("$.totalDeApuestas").value(1))
                .andExpect(jsonPath("$.totalPagado").value(3500))
                .andExpect(jsonPath("$.totalEnReserva").value(996500));

    }
}



package com.proyectoyomi.dbnoticias.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectoyomi.dbnoticias.dto.AprehensionDto;
import com.proyectoyomi.dbnoticias.dto.DataRequestDto;
import com.proyectoyomi.dbnoticias.dto.FechaDto;
import com.proyectoyomi.dbnoticias.dto.ImplicadosDto;
import com.proyectoyomi.dbnoticias.model.Aprehension;
import com.proyectoyomi.dbnoticias.model.Localizacion;
import com.proyectoyomi.dbnoticias.model.Operacion;
import com.proyectoyomi.dbnoticias.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ConsultaServiceTest {

    @Mock
    private AprehensionRepository aprehensionRepository;
    @Mock
    private LocalizacionRepository localizacionRepository;

    @Mock
    private OperacionRepository operacionRepository;
    @InjectMocks
    private ConsultaService consultaService;

    @Test
    public void resultadoBusqueda_success() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestTest = "{\"provincias\": [\"cadiz\", \"barcelona\"], \"drogas\": {\"hachisMinimo\": 0, " +
                "\"hachisMaximo\": 0, \"cocainaMinimo\": 0, \"cocainaMaximo\": 5000, \"marihuanaMinimo\": 0, " +
                "\"marihuanaMaximo\": 5000, \"plantacionesMinimo\": 0, \"plantacionesMaximo\": 0, " +
                "\"plantasMinimo\": 11111,  \"plantasMaximo\": 999999}, \"implicados\": " +
                "{\"implicadosMinimo\": 0, \"implicadosMaximo\": 100}, \"fecha\": " +
                "{ \"fechaInicio\": \"2023-11-29\", \"fechaFinal\": \"2023-12-30\" }}";
        DataRequestDto dataRequest = objectMapper.readValue(jsonRequestTest, DataRequestDto.class);
        FechaDto fechaDto = dataRequest.getFecha();
        ImplicadosDto implicadosDto = dataRequest.getImplicados();


        List<Operacion> operacionList = new ArrayList<>();
        Operacion operacion1 = new Operacion(1L, "aprehension","01-12-2023", "operacion1", 10);
        Operacion operacion2 = new Operacion(2L,"aprehension","02-12-2023", "operacion2", 11);
        operacionList.add(operacion1);
        operacionList.add(operacion2);

        when(operacionRepository.searchOperacionByImplicadosAndFecha(implicadosDto.getImplicadosMinimo(),
                implicadosDto.getImplicadosMaximo(),
                fechaDto.getFechaInicio(), fechaDto.getFechaFinal())).thenReturn(operacionList);

        List<Localizacion> localizaciones = new ArrayList<>();
        Localizacion localizacion1 = new Localizacion("españa", "galicia", "pomntevedra", "car", "barrio", "call");
        Localizacion localizacion2 = new Localizacion("españa", "madrid", "florida", "car", "barrio", "call");
        localizaciones.add(localizacion1);
        localizaciones.add(localizacion2);
        ArrayList<String> provincia = new ArrayList<>();
        provincia.add("cadiz");
        provincia.add("barcelona");

        when(localizacionRepository.findOperacionByProvincia(1L, provincia)).thenReturn(localizaciones);
        when(localizacionRepository.findOperacionByProvincia(2L, provincia)).thenReturn(localizaciones);

        AprehensionDto aprehensionPetition = dataRequest.getDrogas();
        List<String[]> aprehensionPetitionList = aprehensionPetition.convertToList();
        aprehensionPetitionList.removeIf(a -> Integer.parseInt(a[2]) < 1);

        List<Aprehension> aprehensionList = new ArrayList<>();
        Aprehension aprehension1 = new Aprehension(1L, 5.0f,5.0f,5.0f,5,5,5,"5", 5);
        Aprehension aprehension2 = new Aprehension(2L, 5.0f,5.0f,5.0f,5,5,5,"5", 5);
        aprehensionList.add(aprehension1);
        aprehensionList.add(aprehension2);

        when(aprehensionRepository.buscarPorDrogaExcluyente(any(Long.class), any(List.class))).thenReturn(aprehensionList);
        when(aprehensionRepository.buscarPorDrogaExcluyente(any(Long.class), any(List.class))).thenReturn(aprehensionList);

        String resultado = consultaService.resultadosBusqueda(jsonRequestTest);
        String esperado = "[{\"operacionId\":1,\"accion\":\"aprehension\",\"fecha\":\"01-12-2023\",\"implicados\":10,\"nombreOperacion\":\"operacion1\",\"localizaciones\":[{\"id\":null,\"pais\":\"españa\",\"ccaa\":\"galicia\",\"provincia\":\"pomntevedra\",\"ciudad\":\"car\",\"barrio\":\"barrio\",\"calle\":\"call\"},{\"id\":null,\"pais\":\"españa\",\"ccaa\":\"madrid\",\"provincia\":\"florida\",\"ciudad\":\"car\",\"barrio\":\"barrio\",\"calle\":\"call\"}],\"aprehension\":{\"id\":1,\"marihuana\":5.0,\"cocaina\":5.0,\"hachis\":5.0,\"plantaciones\":5,\"plantas\":5,\"embarcaciones\":5,\"dinero\":\"5\",\"vehiculos\":5}},{\"operacionId\":2,\"accion\":\"aprehension\",\"fecha\":\"02-12-2023\",\"implicados\":11,\"nombreOperacion\":\"operacion2\",\"localizaciones\":[{\"id\":null,\"pais\":\"españa\",\"ccaa\":\"galicia\",\"provincia\":\"pomntevedra\",\"ciudad\":\"car\",\"barrio\":\"barrio\",\"calle\":\"call\"},{\"id\":null,\"pais\":\"españa\",\"ccaa\":\"madrid\",\"provincia\":\"florida\",\"ciudad\":\"car\",\"barrio\":\"barrio\",\"calle\":\"call\"}],\"aprehension\":{\"id\":1,\"marihuana\":5.0,\"cocaina\":5.0,\"hachis\":5.0,\"plantaciones\":5,\"plantas\":5,\"embarcaciones\":5,\"dinero\":\"5\",\"vehiculos\":5}}]";
        assertEquals(esperado, resultado);
    }

    @Test
    public void resultadoBusqueda_NullFechaDto_Exception() {
        String jsonRequestTest = "{\"provincias\": [\"cadiz\", \"barcelona\"], \"drogas\": {\"hachisMinimo\": 0, " +
                "\"hachisMaximo\": 0, \"cocainaMinimo\": 0, \"cocainaMaximo\": 5000, \"marihuanaMinimo\": 0, " +
                "\"marihuanaMaximo\": 5000, \"plantacionesMinimo\": 0, \"plantacionesMaximo\": 0, " +
                "\"plantasMinimo\": 11111,  \"plantasMaximo\": 999999}, \"implicados\": " +
                "{\"implicadosMinimo\": 0, \"implicadosMaximo\": 100}, \"fecha\": null}";

        assertThrows(NullPointerException.class, () -> consultaService.resultadosBusqueda(jsonRequestTest));
    }

    @Test
    public void resultadoBusqueda_NullImplicadosDto_Exception() {
        String jsonRequestTest = "{\"provincias\": [\"cadiz\", \"barcelona\"], \"drogas\": {\"hachisMinimo\": 0, " +
                "\"hachisMaximo\": 0, \"cocainaMinimo\": 0, \"cocainaMaximo\": 5000, \"marihuanaMinimo\": 0, " +
                "\"marihuanaMaximo\": 5000, \"plantacionesMinimo\": 0, \"plantacionesMaximo\": 0, " +
                "\"plantasMinimo\": 11111,  \"plantasMaximo\": 999999}, \"implicados\": null, " +
                "\"fecha\": " +
                "{ \"fechaInicio\": \"2023-11-29\", \"fechaFinal\": \"2023-12-30\" }}";

        assertThrows(NullPointerException.class, () -> consultaService.resultadosBusqueda(jsonRequestTest));
    }


}

package com.proyectoyomi.dbnoticias.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectoyomi.dbnoticias.dto.*;
import com.proyectoyomi.dbnoticias.model.Aprehension;
import com.proyectoyomi.dbnoticias.model.Localizacion;
import com.proyectoyomi.dbnoticias.model.Operacion;
import com.proyectoyomi.dbnoticias.repository.AprehensionRepository;
import com.proyectoyomi.dbnoticias.repository.LocalizacionRepository;
import com.proyectoyomi.dbnoticias.repository.OperacionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



/*
LÓGICA DEL SERVICIO:
1-Buscamos operaciones por fecha + implicados
2-Utilizamos los id de las operaciones devueltas para buscar por ID Operacion + Provincias recibidas
3-Utilizamos los id de las localizaciones para buscar por ID Localizacion + Cantidad de Drogas
*/
@Service
public class ConsultaService {
    private final AprehensionRepository aprehensionRepository;
    private final LocalizacionRepository localizacionRepository;

    private final OperacionRepository operacionRepository;

    public ConsultaService(
            AprehensionRepository aprehensionRepository,
            LocalizacionRepository localizacionRepository,
            OperacionRepository operacionRepository
    ) {
        this.aprehensionRepository = aprehensionRepository;
        this.localizacionRepository = localizacionRepository;
        this.operacionRepository = operacionRepository;
    }


    // Resultados de la búsqueda
    public String resultadosBusqueda(String dataRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DataRequestDto data = objectMapper.readValue(dataRequest, DataRequestDto.class);
        ArrayList<String> provinciasPetition = data.getProvincias();
        provinciasPetition.replaceAll(String::toLowerCase);
        AprehensionDto aprehensionPetition = data.getDrogas();

        List<ResponseDto> resultados = new ArrayList<>();

        // Llamamos a la funcion del service de buscar las operaciones y lo metemos en array de resultados
        List<Operacion> resultadosFechaImplicados = new ArrayList<>();
        try {
            List<Operacion> requestImplicadosFecha= findOperacionByImplicadosAndFecha(
                    data.getImplicados(), data.getFecha()
            );
            resultadosFechaImplicados.addAll(requestImplicadosFecha);
        } catch (NullPointerException e) {
            throw new NullPointerException("ImplicadosDto o FechaDto es null");
        }


        for (Operacion operacion : resultadosFechaImplicados) {
            ResponseDto resultado = new ResponseDto();
            resultado.setOperacionId(operacion.getId());
            resultado.setImplicados(operacion.getTotalImplicados());
            resultado.setFecha(operacion.getFecha());
            resultado.setAccion(operacion.getAccion());
            resultado.setNombreOperacion(operacion.getNombre());
            resultados.add(resultado);
        }

        /*
        Por cada objeto de resultados cogemos el ID Operacion y hacemos una
        llamada a funcion de buscar provincias del service, enviando el ID
        y el listado de provincias recibido y recibiendo una listado de localizaciones
        Se actualiza el field de localizaciones del objeto y luego se hace una
        iteración para borrar los que estén vacios.
         */
        for (ResponseDto resultado : resultados) {
            Long idOperacion = resultado.getOperacionId();
            List<Localizacion> localizaciones = findOperacionByProvincia(idOperacion, provinciasPetition);
            resultado.setLocalizaciones(localizaciones);
        }
        resultados.removeIf(r -> r.getLocalizaciones().isEmpty());

        for (ResponseDto resultado : resultados) {
            Long idOperacion = resultado.getOperacionId();
            List<Aprehension> aprehension = buscaPorAprehensionExcluyente(idOperacion, aprehensionPetition);
            if (!aprehension.isEmpty()) {
                resultado.setAprehension(aprehension.get(0));
            }
        }
        resultados.removeIf(r -> r.getAprehension() == null);

        return objectMapper.writeValueAsString(resultados);
    }


    // Buscamos operaciones por fecha e implicados
    public List<Operacion> findOperacionByImplicadosAndFecha(ImplicadosDto implicados, FechaDto fecha) {
        Integer implicadosMinimo = implicados.getImplicadosMinimo();
        Integer implicadosMaximo = implicados.getImplicadosMaximo();
        String fechaInicio = fecha.getFechaInicio();
        String fechaFinal = fecha.getFechaFinal();
        return operacionRepository.searchOperacionByImplicadosAndFecha(
                implicadosMinimo, implicadosMaximo, fechaInicio, fechaFinal
        );
    }


    // Buscamos por provincia
    public List<Localizacion> findOperacionByProvincia(Long idOperacion, ArrayList<String> provincias) {
        return localizacionRepository.findOperacionByProvincia(idOperacion, provincias);
    }


    // Recibimos id operación tratado y dto de la request de aprehension, filtramos datos y devolvemos listado aprehensiones
    public List<Aprehension> buscaPorAprehensionExcluyente(Long idOperacion, AprehensionDto aprehensionPetition)  {
        // Convertimos a listado de String[], y comprobamos si el máximo (String[2]) es inferior a 1 que lo eliminamos
        List<String[]> aprehensionPetitionList = aprehensionPetition.convertToList();
        aprehensionPetitionList.removeIf(a -> Integer.parseInt(a[2]) < 1);
        return aprehensionRepository.buscarPorDrogaExcluyente(idOperacion, aprehensionPetitionList);
    }

}

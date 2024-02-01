package com.proyectoyomi.dbnoticias.service;

import com.proyectoyomi.dbnoticias.model.Noticia;
import com.proyectoyomi.dbnoticias.repository.NoticiaRepository;
import com.proyectoyomi.dbnoticias.utils.CSVUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NoticiaService {

    private final NoticiaRepository noticiaRepository;


    public NoticiaService(NoticiaRepository noticiaRepository) {
        this.noticiaRepository = noticiaRepository;
    }

    public List<Noticia> findAll() {
        return noticiaRepository.findAll();
    }

    public Optional<Noticia> findById(Long id) throws Exception{

        if (id < 0) {
            // TODO: Catch exception / Devolver a frontend imposibilidad y no ejecutar aquí la función
            throw new ArrayIndexOutOfBoundsException("El número no puede ser negativo");
        }
        Optional<Noticia> noticia = noticiaRepository.findById(id);
        if (noticia.isEmpty()) throw new Exception("No se ha localizado el ID");
        return noticia;

    }

    // Busca noticias por condición de revisado, si no hay se lanza error
    public Noticia getNoticiaByRevisado(Boolean condicion) {
        Optional<List<Noticia>> listadoNoticias = noticiaRepository.findFirstByNoticiabyRevisado(condicion);
        try {
            return listadoNoticias.get().get(0);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("No hay noticias disponibles");
        }
    }

    public void volcadoCSV(MultipartFile archivo) {
        List<String> records = CSVUtils.readCSV(archivo);
        // Asigna a cada linea de la extracción un objeto Noticia, spliteando en los separadores
        // de ;, ya que recibe la lectura como una sola string por línea
        for (String record : records) {
            try {
                String[] splitString = record.split(";");
                Noticia nuevaNoticia = new Noticia(
                        splitString[0].substring(1, splitString[0].length() - 1),
                        splitString[1].substring(1, splitString[1].length() - 1),
                        splitString[2].substring(1, splitString[2].length() - 1),
                        splitString[3].substring(1, splitString[3].length() - 1)
                );
                addNewNoticia(nuevaNoticia);
            } catch (Exception e) {
                //TODO: insertar en logger mensaje de error
            }
        }
    }

    public void addNewNoticia(Noticia noticia) throws Exception {
        Optional<Noticia> noticiaByUrl = noticiaRepository.findNoticiabyUrl(noticia.getUrl());

        // Chequeamos si la noticia existe o si el enlace es una
        // redirección de Google a sí mismo, entonces volcamos
        if (noticia.getUrl().contains("www.google")) {
            throw new Exception("Es un elemento de Google");
        } else if (noticiaByUrl.isPresent()) {
            throw new Exception("La noticia existe");
        } else {
            noticiaRepository.save(noticia);
        }
    }

    // Seteamos automáticamente el volcado en falso si está relacionado y null si no
    @Transactional
    public void resultadoRevisionNoticia(Noticia noticia) {
        try {
            if (noticia.getRelacionado()) {
                noticia.setVolcado(false);
            } else {
                noticia.setVolcado(null);
            }
            noticiaRepository.updateRelacionRevisionNoticia(
                    noticia.getRevisado(),
                    noticia.getRelacionado(),
                    noticia.getId(),
                    noticia.getVolcado()
            );
        } catch (NullPointerException e) {
            throw new NullPointerException("El campo de Relacionado no puede estar vacío");
        }
    }
}

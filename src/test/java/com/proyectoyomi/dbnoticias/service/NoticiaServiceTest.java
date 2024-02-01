package com.proyectoyomi.dbnoticias.service;

import com.proyectoyomi.dbnoticias.model.Noticia;

import com.proyectoyomi.dbnoticias.repository.NoticiaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoticiaServiceTest {

    @Mock
    private NoticiaRepository noticiaRepository;

    Noticia noticia1 = new Noticia(1L, "url1", "titular1", "subtitular1", "fecha1", true, true, true);
    Noticia noticia1Repetida = new Noticia(2L, "url1", "titular1", "subtitular1", "fecha1", true, false, false);
    Noticia noticia2 = new Noticia(3L, "www.google.com", "titular2", "subtitular2", "fecha2", false, false, false);

    @InjectMocks
    private NoticiaService noticiaService;


    @Test
    public void findAllNoticias_Success()  {
        List<Noticia> noticias = new ArrayList<>(Arrays.asList(noticia1, noticia1Repetida, noticia2));
        when(noticiaRepository.findAll()).thenReturn(noticias);

        List<Noticia> testList = noticiaService.findAll();
        assertEquals(3, testList.size());
        Mockito.verify(noticiaRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findById_success() throws Exception {
        when(noticiaRepository.findById(1L)).thenReturn(java.util.Optional.of(noticia1));
        Optional<Noticia> noticiaTest = noticiaService.findById(1L);
        assertEquals(1L, noticiaTest.get().getId());
    }

    @Test
    public void findById_NotExistingId_Exception() {
        when(noticiaRepository.findById(4L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> noticiaService.findById(4L));
    }

    @Test
    public void findById_NegativeId_Exception() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> noticiaService.findById(-1L));
    }

    @Test
    public void getNoticiaByRevisado_success() {
        // Primero por True y luego por False
        List<Noticia> listadoNoticiaTrue = new ArrayList<>(List.of(noticia1, noticia1Repetida));
        List<Noticia> listadoNoticiaFalse = new ArrayList<>(List.of(noticia2));
        when(noticiaRepository.findFirstByNoticiabyRevisado(true)).thenReturn(Optional.of(listadoNoticiaTrue));
        when(noticiaRepository.findFirstByNoticiabyRevisado(false)).thenReturn(Optional.of(listadoNoticiaFalse));

        Noticia noticiaTrue = noticiaService.getNoticiaByRevisado(true);
        Noticia noticiaFalse = noticiaService.getNoticiaByRevisado(false);

        assertEquals(true, noticiaTrue.getRevisado());
        assertEquals( false, noticiaFalse.getRevisado());
    }

    @Test
    public void getNoticiaByRevisado_Exception() {
        assertThrows(NoSuchElementException.class, () -> noticiaService.getNoticiaByRevisado(true));
    }

    @Test
    public void addNewNoticia_success() throws Exception {
        noticiaService.addNewNoticia(noticia1);
        Mockito.verify(noticiaRepository, times(1)).save(noticia1);
    }

    @Test
    public void addNewNoticia_NoticiaExiste_Exception() {
        when(noticiaRepository.findNoticiabyUrl(any(String.class))).thenReturn(Optional.of(noticia1Repetida));
        assertThrows(Exception.class, () -> noticiaService.addNewNoticia(noticia1Repetida));
    }

    @Test
    public void addNewNoticia_GoogleNoticia_Exception() {
        assertThrows(Exception.class, () -> noticiaService.addNewNoticia(noticia2));
    }

    @Test
    public void resultadoRevisionNoticia_success() {
        noticiaService.resultadoRevisionNoticia(noticia1);

        Mockito.verify(noticiaRepository, times(1)).updateRelacionRevisionNoticia(
                noticia1.getRevisado(),
                noticia1.getRelacionado(),
                noticia1.getId(),
                noticia1.getVolcado());
    }

    @Test
    public void resultadoRevisionNoticia_NullRelacionado_Exception() {
        Noticia noticiaTest = new Noticia();
        assertThrows(NullPointerException.class, () -> noticiaService.resultadoRevisionNoticia(noticiaTest));
    }

}


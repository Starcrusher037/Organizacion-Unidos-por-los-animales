package com.duoc.backend;

import com.duoc.backend.Care.Care;
import com.duoc.backend.Care.CareRepository;
import com.duoc.backend.Care.CareService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CareServiceTest {

    @Mock
    private CareRepository careRepository;

    @InjectMocks
    private CareService careService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCares() {
        Care care1 = new Care();
        care1.setId(1L);
        care1.setName("Vacuna");
        care1.setCost(10000.0);

        Care care2 = new Care();
        care2.setId(2L);
        care2.setName("Consulta");
        care2.setCost(15000.0);

        when(careRepository.findAll()).thenReturn(Arrays.asList(care1, care2));

        var result = careService.getAllCares();

        assertEquals(2, result.size());
        verify(careRepository, times(1)).findAll();
    }

    @Test
    void testGetCareById_found() {
        Care care = new Care();
        care.setId(1L);

        when(careRepository.findById(1L)).thenReturn(Optional.of(care));

        Care result = careService.getCareById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetCareById_notFound() {
        when(careRepository.findById(1L)).thenReturn(Optional.empty());

        Care result = careService.getCareById(1L);

        assertNull(result);
    }

    @Test
    void testSaveCare() {
        Care care = new Care();
        care.setName("Baño");

        when(careRepository.save(care)).thenReturn(care);

        Care result = careService.saveCare(care);

        assertNotNull(result);
        assertEquals("Baño", result.getName());
        verify(careRepository, times(1)).save(care);
    }

    @Test
    void testDeleteCare() {
        doNothing().when(careRepository).deleteById(1L);

        careService.deleteCare(1L);

        verify(careRepository, times(1)).deleteById(1L);
    }
}
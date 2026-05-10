package com.duoc.backend;

import com.duoc.backend.Medication.Medication;
import com.duoc.backend.Medication.MedicationRepository;
import com.duoc.backend.Medication.MedicationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationService medicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMedications() {
        Medication m1 = new Medication();
        m1.setId(1L);

        Medication m2 = new Medication();
        m2.setId(2L);

        when(medicationRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        var result = medicationService.getAllMedications();

        assertEquals(2, result.size());
        verify(medicationRepository, times(1)).findAll();
    }

    @Test
    void testGetMedicationById_found() {
        Medication m = new Medication();
        m.setId(1L);

        when(medicationRepository.findById(1L)).thenReturn(Optional.of(m));

        Medication result = medicationService.getMedicationById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetMedicationById_notFound() {
        when(medicationRepository.findById(1L)).thenReturn(Optional.empty());

        Medication result = medicationService.getMedicationById(1L);

        assertNull(result);
    }

    @Test
    void testSaveMedication() {
        Medication m = new Medication();
        m.setName("Test");

        when(medicationRepository.save(m)).thenReturn(m);

        Medication result = medicationService.saveMedication(m);

        assertNotNull(result);
        assertEquals("Test", result.getName());
        verify(medicationRepository, times(1)).save(m);
    }

    @Test
    void testDeleteMedication() {
        doNothing().when(medicationRepository).deleteById(1L);

        medicationService.deleteMedication(1L);

        verify(medicationRepository, times(1)).deleteById(1L);
    }
}
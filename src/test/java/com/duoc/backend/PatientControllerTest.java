package com.duoc.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.duoc.backend.Patient.PatientService;
import com.duoc.backend.Patient.PatientController;
import com.duoc.backend.Patient.Patient;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPatients() {

        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setName("Firulais");
        patient1.setSpecies("Perro");
        patient1.setBreed("Labrador");
        patient1.setAge(5);
        patient1.setOwner("Juan Pérez");

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setName("Michi");
        patient2.setSpecies("Gato");
        patient2.setBreed("Siames");
        patient2.setAge(3);
        patient2.setOwner("Ana Gómez");

        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientService.getAllPatients()).thenReturn(patients);

        List<Patient> result = patientController.getAllPatients();

        assertEquals(2, result.size());
        assertEquals("Firulais", result.get(0).getName());
        assertEquals("Michi", result.get(1).getName());
        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    void testGetPatientById() {

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Firulais");
        patient.setSpecies("Perro");
        patient.setBreed("Labrador");
        patient.setAge(5);
        patient.setOwner("Juan Pérez");

        when(patientService.getPatientById(1L)).thenReturn(patient);

        Patient result = patientController.getPatientById(1L);

        assertEquals("Firulais", result.getName());
        assertEquals("Perro", result.getSpecies());
        verify(patientService, times(1)).getPatientById(1L);
    }

    @Test
    void testSavePatient() {

        Patient patient = new Patient();
        patient.setName("Firulais");
        patient.setSpecies("Perro");
        patient.setBreed("Labrador");
        patient.setAge(5);
        patient.setOwner("Juan Pérez");

        when(patientService.savePatient(patient)).thenReturn(patient);

        Patient result = patientController.savePatient(patient);

        assertEquals("Firulais", result.getName());
        verify(patientService, times(1)).savePatient(patient);
    }

    @Test
    void testDeletePatient() {

        Long patientId = 1L;

        patientController.deletePatient(patientId);

        verify(patientService, times(1)).deletePatient(patientId);
    }
}
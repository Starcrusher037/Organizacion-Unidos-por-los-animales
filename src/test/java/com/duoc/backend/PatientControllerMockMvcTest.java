package com.duoc.backend;

import com.duoc.backend.Patient.PatientController;
import com.duoc.backend.Patient.PatientService;
import com.duoc.backend.Patient.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
class PatientControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void testGetAllPatientsWithMockBearerToken() throws Exception {

        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setName("Firulais");
        patient1.setSpecies("Perro");
        patient1.setBreed("Labrador");
        patient1.setAge(5);
        patient1.setOwner("Juan Pérez");

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setName("Tom");
        patient2.setSpecies("Gato");
        patient2.setBreed("Comun");
        patient2.setAge(5);
        patient2.setOwner("Montse");

        when(patientService.getAllPatients()).thenReturn(Arrays.asList(patient1, patient2));

        mockMvc.perform(get("/patient")
                .header("Authorization", "Bearer mock-token")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Firulais"))
                .andExpect(jsonPath("$[1].name").value("Tom"));

        verify(patientService, times(1)).getAllPatients();
    }
}
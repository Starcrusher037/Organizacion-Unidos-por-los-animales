package com.duoc.backend;

import com.duoc.backend.Medication.Medication;
import com.duoc.backend.Medication.MedicationController;
import com.duoc.backend.Medication.MedicationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicationController.class)
@AutoConfigureMockMvc(addFilters = false)
class MedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicationService medicationService;

    @Test
    void testGetAllMedications() throws Exception {
        Medication m1 = new Medication();
        m1.setId(1L);
        m1.setName("Antibiótico");
        m1.setCost(12000.0);

        Medication m2 = new Medication();
        m2.setId(2L);
        m2.setName("Vacuna");
        m2.setCost(8000.0);

        when(medicationService.getAllMedications()).thenReturn(Arrays.asList(m1, m2));

        mockMvc.perform(get("/medication"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Antibiótico"))
                .andExpect(jsonPath("$[1].name").value("Vacuna"));

        verify(medicationService, times(1)).getAllMedications();
    }

    @Test
    void testGetMedicationById() throws Exception {
        Medication m = new Medication();
        m.setId(1L);
        m.setName("Antibiótico");

        when(medicationService.getMedicationById(1L)).thenReturn(m);

        mockMvc.perform(get("/medication/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Antibiótico"));

        verify(medicationService, times(1)).getMedicationById(1L);
    }

    @Test
    void testSaveMedication() throws Exception {
        Medication m = new Medication();
        m.setId(1L);
        m.setName("Jarabe");
        m.setCost(5000.0);

        when(medicationService.saveMedication(any(Medication.class))).thenReturn(m);

        mockMvc.perform(post("/medication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Jarabe",
                                    "cost": 5000
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jarabe"));

        verify(medicationService, times(1)).saveMedication(any(Medication.class));
    }

    @Test
    void testDeleteMedication() throws Exception {
        doNothing().when(medicationService).deleteMedication(1L);

        mockMvc.perform(delete("/medication/1"))
                .andExpect(status().isOk());

        verify(medicationService, times(1)).deleteMedication(1L);
    }
}
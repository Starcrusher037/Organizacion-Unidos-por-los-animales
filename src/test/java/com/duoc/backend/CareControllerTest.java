package com.duoc.backend;

import com.duoc.backend.Care.Care;
import com.duoc.backend.Care.CareController;
import com.duoc.backend.Care.CareRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CareController.class)
@AutoConfigureMockMvc(addFilters = false) 
class CareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CareRepository careRepository;

    @Test
    void testGetAllCares() throws Exception {
        Care care1 = new Care();
        care1.setId(1L);
        care1.setName("Vacunación");
        care1.setCost(10000.0);

        Care care2 = new Care();
        care2.setId(2L);
        care2.setName("Desparasitación");
        care2.setCost(8000.0);

        when(careRepository.findAll()).thenReturn(Arrays.asList(care1, care2));

        mockMvc.perform(get("/care"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Vacunación"))
                .andExpect(jsonPath("$[1].name").value("Desparasitación"));

        verify(careRepository, times(1)).findAll();
    }

    @Test
    void testGetCareById() throws Exception {
        Care care = new Care();
        care.setId(1L);
        care.setName("Cirugía");
        care.setCost(50000.0);

        when(careRepository.findById(1L)).thenReturn(Optional.of(care));

        mockMvc.perform(get("/care/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cirugía"));

        verify(careRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveCare() throws Exception {
        Care care = new Care();
        care.setId(1L);
        care.setName("Baño");
        care.setCost(5000.0);

        when(careRepository.save(any(Care.class))).thenReturn(care);

        mockMvc.perform(post("/care")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Baño",
                                    "cost": 5000
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Baño"));

        verify(careRepository, times(1)).save(any(Care.class));
    }

    @Test
    void testDeleteCare() throws Exception {
        doNothing().when(careRepository).deleteById(1L);

        mockMvc.perform(delete("/care/1"))
                .andExpect(status().isOk());

        verify(careRepository, times(1)).deleteById(1L);
    }
}
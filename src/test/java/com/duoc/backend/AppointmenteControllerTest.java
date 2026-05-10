package com.duoc.backend;

import com.duoc.backend.Appointment.Appointment;
import com.duoc.backend.Appointment.AppointmentController;
import com.duoc.backend.Appointment.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Test
    @WithMockUser
    void testGetAllAppointments() throws Exception {
        Appointment a1 = new Appointment();
        a1.setId(1L);
        a1.setReason("Control");
        a1.setVeterinarian("Dr. Lopez");
        a1.setDate(LocalDate.now());
        a1.setTime(LocalTime.now());

        Appointment a2 = new Appointment();
        a2.setId(2L);
        a2.setReason("Vacuna");
        a2.setVeterinarian("Dr. Perez");
        a2.setDate(LocalDate.now());
        a2.setTime(LocalTime.now());

        when(appointmentService.getAllAppointments()).thenReturn(Arrays.asList(a1, a2));

        mockMvc.perform(get("/appointment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(appointmentService, times(1)).getAllAppointments();
    }

    @Test
    @WithMockUser
    void testGetAppointmentById() throws Exception {
        Appointment a = new Appointment();
        a.setId(1L);
        a.setReason("Control");

        when(appointmentService.getAppointmentById(1L)).thenReturn(a);

        mockMvc.perform(get("/appointment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("Control"));

        verify(appointmentService, times(1)).getAppointmentById(1L);
    }

    @Test
    @WithMockUser
    void testSaveAppointment() throws Exception {
        Appointment a = new Appointment();
        a.setId(1L);
        a.setReason("Cirugía");

        when(appointmentService.saveAppointment(any(Appointment.class))).thenReturn(a);

        String json = """
                {
                    "reason": "Cirugía"
                }
                """;

        mockMvc.perform(post("/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("Cirugía"));

        verify(appointmentService, times(1)).saveAppointment(any(Appointment.class));
    }

    @Test
    @WithMockUser
    void testDeleteAppointment() throws Exception {
        doNothing().when(appointmentService).deleteAppointment(1L);

        mockMvc.perform(delete("/appointment/1"))
                .andExpect(status().isOk());

        verify(appointmentService, times(1)).deleteAppointment(1L);
    }
}
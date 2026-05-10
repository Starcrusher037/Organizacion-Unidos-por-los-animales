package com.duoc.backend;

import org.junit.jupiter.api.Test;

import com.duoc.backend.Appointment.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void testGettersAndSetters() {
        Appointment appointment = new Appointment();

        Long id = 1L;
        LocalDate date = LocalDate.of(2026, 4, 28);
        LocalTime time = LocalTime.of(15, 45);
        String reason = "Control general";
        String veterinarian = "Dr. Lopez";

        // Setters
        appointment.setId(id);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setReason(reason);
        appointment.setVeterinarian(veterinarian);

        // Getters + asserts
        assertEquals(id, appointment.getId());
        assertEquals(date, appointment.getDate());
        assertEquals(time, appointment.getTime());
        assertEquals(reason, appointment.getReason());
        assertEquals(veterinarian, appointment.getVeterinarian());
    }

    @Test
    void testEmptyAppointment() {
        Appointment appointment = new Appointment();

        assertNull(appointment.getId());
        assertNull(appointment.getDate());
        assertNull(appointment.getTime());
        assertNull(appointment.getReason());
        assertNull(appointment.getVeterinarian());
    }
}
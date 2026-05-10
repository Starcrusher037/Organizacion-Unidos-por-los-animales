package com.duoc.backend;

import org.junit.jupiter.api.Test;
import com.duoc.backend.Medication.Medication;
import static org.junit.jupiter.api.Assertions.*;

class MedicationTest {

    @Test
    void testGettersAndSetters() {
        Medication medication = new Medication();

        Long id = 1L;
        String name = "Antibiótico";
        Double cost = 8000.0;

        // Setters
        medication.setId(id);
        medication.setName(name);
        medication.setCost(cost);

        // Getters + asserts
        assertEquals(id, medication.getId());
        assertEquals(name, medication.getName());
        assertEquals(cost, medication.getCost());
    }

    @Test
    void testEmptyMedication() {
        Medication medication = new Medication();

        assertNull(medication.getId());
        assertNull(medication.getName());
        assertNull(medication.getCost());
    }
}
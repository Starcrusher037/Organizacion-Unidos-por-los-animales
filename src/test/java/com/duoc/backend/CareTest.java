package com.duoc.backend;

import org.junit.jupiter.api.Test;

import com.duoc.backend.Care.Care;

import static org.junit.jupiter.api.Assertions.*;

class CareTest {

    @Test
    void testGettersAndSetters() {
        Care care = new Care();

        Long id = 1L;
        String name = "Vacunación";
        Double cost = 5000.0;

        // Setters
        care.setId(id);
        care.setName(name);
        care.setCost(cost);

        // Getters + asserts
        assertEquals(id, care.getId());
        assertEquals(name, care.getName());
        assertEquals(cost, care.getCost());
    }

    @Test
    void testEmptyCare() {
        Care care = new Care();

        assertNull(care.getId());
        assertNull(care.getName());
        assertNull(care.getCost());
    }
}
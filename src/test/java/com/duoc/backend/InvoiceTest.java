package com.duoc.backend;

import com.duoc.backend.Care.Care;
import com.duoc.backend.Invoice.Invoice;
import com.duoc.backend.Medication.Medication;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @Test
    void testGettersAndSetters() {
        Invoice invoice = new Invoice();

        Long id = 1L;
        String patientName = "Juan Perez";
        LocalDate date = LocalDate.of(2026, 4, 28);
        LocalTime time = LocalTime.of(10, 30);
        Double totalCost = 15000.0;

        Care care1 = new Care();
        care1.setName("Vacuna");
        care1.setCost(5000.0);

        Care care2 = new Care();
        care2.setName("Consulta");
        care2.setCost(10000.0);

        List<Care> cares = Arrays.asList(care1, care2);

        Medication med1 = new Medication();
        med1.setName("Paracetamol");
        med1.setCost(2000.0);

        Medication med2 = new Medication();
        med2.setName("Antibiotico");
        med2.setCost(3000.0);

        List<Medication> medications = Arrays.asList(med1, med2);

        invoice.setId(id);
        invoice.setPatientName(patientName);
        invoice.setDate(date);
        invoice.setTime(time);
        invoice.setCares(cares);
        invoice.setMedications(medications);
        invoice.setTotalCost(totalCost);

        assertEquals(id, invoice.getId());
        assertEquals(patientName, invoice.getPatientName());
        assertEquals(date, invoice.getDate());
        assertEquals(time, invoice.getTime());
        assertEquals(cares, invoice.getCares());
        assertEquals(medications, invoice.getMedications());
        assertEquals(totalCost, invoice.getTotalCost());
    }

    @Test
    void testModifyInvoiceData() {
        Invoice invoice = new Invoice();

        invoice.setPatientName("Inicial");
        assertEquals("Inicial", invoice.getPatientName());

        invoice.setPatientName("Modificado");
        assertEquals("Modificado", invoice.getPatientName());

        invoice.setTotalCost(1000.0);
        invoice.setTotalCost(2000.0);
        assertEquals(2000.0, invoice.getTotalCost());
    }

    @Test
    void testCaresAndMedicationsUpdate() {
        Invoice invoice = new Invoice();

        List<Care> cares = Arrays.asList(new Care(), new Care());
        invoice.setCares(cares);
        assertEquals(2, invoice.getCares().size());

        List<Medication> meds = Arrays.asList(new Medication());
        invoice.setMedications(meds);
        assertEquals(1, invoice.getMedications().size());
    }

    @Test
    void testEmptyInvoice() {
        Invoice invoice = new Invoice();

        assertNull(invoice.getId());
        assertNull(invoice.getPatientName());
        assertNull(invoice.getDate());
        assertNull(invoice.getTime());
        assertNull(invoice.getCares());
        assertNull(invoice.getMedications());
        assertNull(invoice.getTotalCost());
    }
}
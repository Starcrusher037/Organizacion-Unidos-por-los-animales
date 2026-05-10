package com.duoc.backend;

import com.duoc.backend.Care.Care;
import com.duoc.backend.Care.CareRepository;
import com.duoc.backend.Invoice.Invoice;
import com.duoc.backend.Invoice.InvoiceRepository;
import com.duoc.backend.Invoice.InvoiceService;
import com.duoc.backend.Medication.Medication;
import com.duoc.backend.Medication.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private CareRepository careRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    void testGetAllInvoices() {
        invoiceService.getAllInvoices();
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    void testGetInvoiceById() {
        Invoice invoice = new Invoice();
        invoice.setId(1L);

        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        Invoice result = invoiceService.getInvoiceById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSaveInvoiceSuccess() {
        Invoice invoice = new Invoice();

        Care care = new Care();
        care.setId(1L);
        care.setCost(1000.0);

        Medication med = new Medication();
        med.setId(1L);
        med.setCost(500.0);

        invoice.setCares(Arrays.asList(care));
        invoice.setMedications(Arrays.asList(med));

        when(careRepository.findAllById(any())).thenReturn(Arrays.asList(care));
        when(medicationRepository.findAllById(any())).thenReturn(Arrays.asList(med));
        when(invoiceRepository.save(any())).thenReturn(invoice);

        Invoice result = invoiceService.saveInvoice(invoice);

        assertEquals(1500.0, result.getTotalCost());
        verify(invoiceRepository).save(invoice);
    }

    @Test
    void testSaveInvoiceInvalidMedication() {
        Invoice invoice = new Invoice();

        Medication med = new Medication();
        med.setId(1L);

        invoice.setMedications(Arrays.asList(med));
        invoice.setCares(Arrays.asList());

        when(medicationRepository.findAllById(any())).thenReturn(Arrays.asList()); // vacío → error

        assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.saveInvoice(invoice);
        });
    }

    @Test
    void testSaveInvoiceInvalidCare() {
        Invoice invoice = new Invoice();

        Care care = new Care();
        care.setId(1L);

        invoice.setCares(Arrays.asList(care));
        invoice.setMedications(Arrays.asList());

        when(careRepository.findAllById(any())).thenReturn(Arrays.asList()); // error

        assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.saveInvoice(invoice);
        });
    }

    @Test
    void testDeleteInvoice() {
        invoiceService.deleteInvoice(1L);
        verify(invoiceRepository).deleteById(1L);
    }
}
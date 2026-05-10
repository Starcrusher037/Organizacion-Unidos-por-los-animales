package com.duoc.backend;

import com.duoc.backend.Care.Care;
import com.duoc.backend.Invoice.Invoice;
import com.duoc.backend.Invoice.InvoiceController;
import com.duoc.backend.Invoice.InvoiceService;
import com.duoc.backend.Medication.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceController.class)
@AutoConfigureMockMvc(addFilters = false) 
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Test
    void testGetAllInvoices() throws Exception {
        when(invoiceService.getAllInvoices()).thenReturn(Arrays.asList(new Invoice(), new Invoice()));

        mockMvc.perform(get("/invoice"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetInvoiceById() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setId(1L);

        when(invoiceService.getInvoiceById(1L)).thenReturn(invoice);

        mockMvc.perform(get("/invoice/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testSaveInvoice() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setPatientName("Test");

        when(invoiceService.saveInvoice(any())).thenReturn(invoice);

        mockMvc.perform(post("/invoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"patientName\":\"Test\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteInvoice() throws Exception {
        mockMvc.perform(delete("/invoice/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGeneratePdfSuccess() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setPatientName("Test");
        invoice.setTotalCost(1000.0);

        Care care = new Care();
        care.setName("Consulta");
        care.setCost(500.0);

        Medication med = new Medication();
        med.setName("Medicamento");
        med.setCost(500.0);

        invoice.setCares(Arrays.asList(care));
        invoice.setMedications(Arrays.asList(med));

        when(invoiceService.getInvoiceById(1L)).thenReturn(invoice);

        mockMvc.perform(get("/invoice/pdf/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGeneratePdfNotFound() throws Exception {
        when(invoiceService.getInvoiceById(1L)).thenReturn(null);

        mockMvc.perform(get("/invoice/pdf/1"))
                .andExpect(status().isNotFound());
    }
}
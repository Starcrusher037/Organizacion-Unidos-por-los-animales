package com.duoc.backend;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:mysql://localhost:3307/veterinary_system",
                "spring.datasource.username=root",
                "spring.datasource.password=root",
                "spring.jpa.hibernate.ddl-auto=none"
        }
)
@AutoConfigureMockMvc
class PatientControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private Environment environment;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    private String obtenerToken() {
        String loginUrl = "http://localhost:" + port + "/login";

        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String loginBody = "user=admin&encryptedPass=1234";

        HttpEntity<String> loginRequest = new HttpEntity<>(loginBody, loginHeaders);

        ResponseEntity<String> loginResponse =
                restTemplate.postForEntity(loginUrl, loginRequest, String.class);

        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());

        return loginResponse.getBody().replace("Bearer ", "");
    }

    @Test
    void testLoginAndGetAllPatients() {
        String patientUrl = "http://localhost:" + port + "/patient";

        String bearerToken = obtenerToken();

        HttpHeaders patientHeaders = new HttpHeaders();
        patientHeaders.setContentType(MediaType.APPLICATION_JSON);
        patientHeaders.setBearerAuth(bearerToken);

        HttpEntity<String> patientRequest = new HttpEntity<>(patientHeaders);

        ResponseEntity<String> patientResponse =
                restTemplate.exchange(patientUrl, HttpMethod.GET, patientRequest, String.class);

        assertEquals(HttpStatus.OK, patientResponse.getStatusCode());
        assertNotNull(patientResponse.getBody());
    }

    @Test
    void testGetAllPatientsWithBearerToken() {
        String patientUrl = "http://localhost:" + port + "/patient";

        String bearerToken = obtenerToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange(patientUrl, HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getMvc() throws Exception {

        String bearerToken = obtenerToken();

        this.mvc.perform(get("/patient")
                        .header("Authorization", "Bearer " + bearerToken))
                .andExpect(status().isOk());
    }
}
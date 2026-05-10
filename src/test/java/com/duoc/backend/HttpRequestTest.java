package com.duoc.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.boot.test.web.client.TestRestTemplate;

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
class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testLoginSuccess() {

        String url = "http://localhost:" + port + "/login?user=admin&encryptedPass=1234";

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().startsWith("Bearer "));
    }

    @Test
    void testLoginInvalidPassword() {

        String url = "http://localhost:" + port + "/login?user=admin&encryptedPass=wrong";

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, null, String.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
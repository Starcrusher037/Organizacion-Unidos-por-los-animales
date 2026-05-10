package com.duoc.backend;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3307/veterinary_system",
        "spring.datasource.username=root",
        "spring.datasource.password=root",
        "spring.jpa.hibernate.ddl-auto=none"
})
class LoginControllerTest {

    @Autowired
    private LoginController loginController;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @Test
    void testLoginControllerCreation() {
        assertNotNull(loginController);
        assertNotNull(userDetailsService);
        assertNotNull(jwtAuthenticationConfig);
    }
}
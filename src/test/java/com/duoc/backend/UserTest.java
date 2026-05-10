package com.duoc.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User user = new User();

        Integer id = 1;
        String username = "admin";
        String password = "1234";
        String email = "admin@test.com";

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
    }

    @Test
    void testUserDetailsMethodsThrowException() {
        User user = new User();

        assertThrows(UnsupportedOperationException.class, user::getAuthorities);
        assertThrows(UnsupportedOperationException.class, user::isAccountNonExpired);
        assertThrows(UnsupportedOperationException.class, user::isAccountNonLocked);
        assertThrows(UnsupportedOperationException.class, user::isCredentialsNonExpired);
        assertThrows(UnsupportedOperationException.class, user::isEnabled);
    }
}
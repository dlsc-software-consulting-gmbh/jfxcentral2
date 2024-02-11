package com.dlsc.jfxcentral2.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisteredUserTest {
    @Test
    public void testUserNameGetterAndSetter() {
        RegisteredUser user = new RegisteredUser();
        user.setUserName("TestUser");
        assertEquals("TestUser", user.getUserName());
    }

    @Test
    public void testFullNameGetterAndSetter() {
        RegisteredUser user = new RegisteredUser();
        user.setFullName("Test User");
        assertEquals("Test User", user.getFullName());
    }

    @Test
    public void testEmailGetterAndSetter() {
        RegisteredUser user = new RegisteredUser();
        user.setEmail("testuser@example.com");
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    public void testConstructor() {
        RegisteredUser user = new RegisteredUser("TestUser", "Test User", "testuser@example.com");
        assertEquals("TestUser", user.getUserName());
        assertEquals("Test User", user.getFullName());
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    public void testToString() {
        RegisteredUser user = new RegisteredUser("TestUser", "Test User", "testuser@example.com");
        assertEquals("RegisteredUser{userName='TestUser', fullName='Test User', email='testuser@example.com'}", user.toString());
    }
}
package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    public void testGetGreeting() {
        String greeting = App.getGreeting();
        assertNotNull(greeting);
        assertTrue(greeting.contains("Hello, World"));
    }
}

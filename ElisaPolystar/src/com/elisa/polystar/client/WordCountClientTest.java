package com.elisa.polystar.client;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class WordCountClientTest {

    @Test
    void testMostCommonWords() {
        ConcurrentHashMap<String, Integer> data = new ConcurrentHashMap<>();
        data.put("hello", 5);
        data.put("world", 3);
        data.put("polystar", 2);

        // Call the method to get the most common words
        Map<String, Integer> result = WordCountClient.mostCommonWords(data, 2);

        // Verify the most common words
        assertEquals(2, result.size());
        assertTrue(result.containsKey("hello"));
        assertTrue(result.containsKey("world"));
        assertEquals(5, result.get("hello"));
        assertEquals(3, result.get("world"));
    }
}

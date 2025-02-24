package com.elisa.polystar.client;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static org.junit.jupiter.api.Assertions.*;

public class WordCountClientTest {

    @Test
    void testMostCommonWords() {
        // Arrange: Prepare sample word frequency data
        Map<String, Integer> input = new HashMap<>();
        input.put("hello", 5);
        input.put("world", 3);
        input.put("java", 2);
        input.put("programming", 7);
        input.put("code", 4);
        input.put("why", 6);

        Map<String, Integer> result = WordCountClient.mostCommonWords(input, 3);

        assertEquals(3, result.size());
        assertTrue(result.containsKey("programming"));
        assertTrue(result.containsKey("why"));
        assertTrue(result.containsKey("hello"));
        assertEquals(7, result.get("programming"));
    }

    @Test
    void testMostCommonWords_EmptyMap() {
        // Arrange: Empty word frequency data
        ConcurrentHashMap<String, Integer> data = new ConcurrentHashMap<>();

        // Act: Retrieve the most common words
        Map<String, Integer> result = WordCountClient.mostCommonWords(data, 2);

        // Assert: Expect an empty result
        assertTrue(result.isEmpty());
    }

    @Test
    void testMostCommonWords_LimitGreaterThanSize() {
        // Arrange: A map with fewer words than requested
        ConcurrentHashMap<String, Integer> data = new ConcurrentHashMap<>();
        data.put("hello", 4);
        data.put("world", 2);

        // Act: Request more words than available
        Map<String, Integer> result = WordCountClient.mostCommonWords(data, 5);

        // Assert: Should return all available words
        assertEquals(Map.of("hello", 4, "world", 2), result);
    }

    @Test
    void testMostCommonWords_TiedFrequencies() {
        // Arrange: Words with the same frequency
        ConcurrentHashMap<String, Integer> data = new ConcurrentHashMap<>();
        data.put("apple", 3);
        data.put("banana", 3);
        data.put("cherry", 2);

        // Act: Request top 2 words
        Map<String, Integer> result = WordCountClient.mostCommonWords(data, 2);

        // Assert: Should return both words with frequency 3
        assertEquals(Map.of("apple", 3, "banana", 3), result);
    }
}

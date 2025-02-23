package com.elisa.polystar.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class WordCountClient {

    private static final int PORT1 = 6500;
    private static final int PORT2 = 6501;
    private static final String HOST = "localhost";

    private static final ConcurrentHashMap<String, Integer> data = new ConcurrentHashMap<>();

    // Java built-in Logger
    private static final Logger logger = Logger.getLogger(WordCountClient.class.getName());

    public static void main(String[] args) {
        Thread client1 = new Thread(() -> readFromServer(PORT1));
        Thread client2 = new Thread(() -> readFromServer(PORT2));

        client1.start();
        client2.start();

        try {
            client1.join();
            client2.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Thread interrupted", e);
            Thread.currentThread().interrupt();
        }

        // Process results after both threads finish
        mostCommonWords(data, 5);
    }

    private static void readFromServer(int port) {
        try (Socket serverSocket = new Socket(HOST, port);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                for (String word : tokens) {
                    word = word.toLowerCase().replaceAll("[^a-zA-Z'-]", ""); // Clean up word
                    if (!word.isEmpty()) {
                        data.merge(word, 1, Integer::sum);
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error connecting to server on port: " + port, e);
        }
    }

    public static void mostCommonWords(Map<String, Integer> input, int limit) {
        Map<String, Integer> result = input.entrySet().stream()
                .sorted((word1, word2) -> Integer.compare(word2.getValue(), word1.getValue()))
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        System.out.println("The 5 most common words in the two files!");
        result.forEach((word, count) -> System.out.println("[" + word + "] : " + count));
    }

}

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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WordCountClient {

    private static final Logger logger = Logger.getLogger(WordCountClient.class.getName());
    private static final ConcurrentHashMap<String, Integer> data = new ConcurrentHashMap<>();
    private static final String host;
    private static final int port1;
    private static final int port2;

    public static final String PROPERTIES = "config.properties";
    public static final String CLEAN_WORD_BOUNDARIES_REGEX = "^[^a-zA-Z']+|[^a-zA-Z']+$";

    // Load configuration properties
    static {
        Properties config = new Properties();
        try (InputStream input = WordCountClient.class.getClassLoader().getResourceAsStream(PROPERTIES)) {
            if (input == null) {
                throw new IOException("Unable to find config.properties file.");
            }
            config.load(input);
            host = config.getProperty("server.host", "localhost");
            port1 = Integer.parseInt(config.getProperty("server.port1", "6500"));
            port2 = Integer.parseInt(config.getProperty("server.port2", "6501"));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to load configuration", ex);
            throw new RuntimeException("Failed to load configuration", ex);
        }
    }

    public static void main(String[] args) {
        Thread client1 = new Thread(() -> readFromServer(port1), "Client-Port-" + port1);
        Thread client2 = new Thread(() -> readFromServer(port2), "Client-Port-" + port2);


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

    static void readFromServer(int port) {
        ConcurrentHashMap<String, Integer> localData = new ConcurrentHashMap<>();

        try (Socket serverSocket = new Socket(host, port);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                for (String word : tokens) {
                    word = word.toLowerCase().replaceAll(CLEAN_WORD_BOUNDARIES_REGEX, ""); // Trim punctuation
                    if (!word.isEmpty()) {
                        localData.merge(word, 1, Integer::sum);
                    }
                }
            }

            // Merge local data into global map
            synchronized (data) {
                localData.forEach((key, value) -> data.merge(key, value, Integer::sum));
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error connecting to server on port: " + port, e);
        }
    }

    public static Map<String, Integer> mostCommonWords(Map<String, Integer> input, int limit) {
        if (input.isEmpty()) {
            logger.info("No words found. The input data is empty.");
            return Map.of();
        }

        Map<String, Integer> result = input.entrySet().stream()
                .sorted((word1, word2) -> Integer.compare(word2.getValue(), word1.getValue()))
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        logger.info("The " + limit + " most common words are:");
        result.forEach((word, count) -> logger.info("[" + word + "] : " + count));

        return result;
    }

}

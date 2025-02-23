package com.elisa.polystar.client;

import java.util.logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class WordCountClient {

    private static final int port1 = 6500;
    private static final int port2 = 6501;
    private static final String host = "localhost";

    private static final ConcurrentHashMap<String, Integer> data = new ConcurrentHashMap<>();

    // Initialize SLF4J logger
    private static final Logger logger = Logger.getLogger(WordCountClient.class.getName());

    public static void main(String[] args) {
        Thread client1 = new Thread(() -> readFromServer(port1));
        Thread client2 = new Thread(() -> readFromServer(port2));

        client1.start();
        client2.start();

        try {
            client1.join();
            client2.join();
        } catch (InterruptedException e) {
            logger.severe("Thread interrupted: "+ e.getMessage());
        }

        // Process results after both threads finish
        mostCommonWords(data, 5);
    }

    private static void readFromServer(int port) {
        try (Socket serverSocket = new Socket(host, port);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                for (String word : tokens) {
                    word = word.toLowerCase().replaceAll("[^a-zA-Z'-]", ""); // Clean up word
                    if (!word.isEmpty()) {
//                        data.merge(word, 1, Integer::sum);
                        data.compute(word, (key, val) -> val == null ? 1 : val + 1);
                    }
                }
            }
        } catch (Exception e) {
            logger.severe("Error connecting to server on port : " + port + " " + e);
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
        result.forEach((word, count) -> System.out.println("[{" + word + "}] : {" + count + "}"));

    }
}

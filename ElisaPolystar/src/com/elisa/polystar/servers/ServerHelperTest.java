package com.elisa.polystar.servers;


import org.junit.jupiter.api.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class ServerHelperTest {

    private static final int TEST_PORT = 12345;
    private static final String TEST_FILE = "test_file.txt";
    private ServerSocket serverSocket;
    private ExecutorService executorService;

    @BeforeEach
    void setUp() throws IOException {
        // Create a test file with sample content
        Files.write(Paths.get(TEST_FILE), "Hello\nWorld\n".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        serverSocket = new ServerSocket(TEST_PORT);
        executorService = Executors.newSingleThreadExecutor();

        // Start the server in a separate thread to prevent blocking
        executorService.submit(() -> {
            try {
                ServerHelper.serverStart(serverSocket, TEST_FILE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @AfterEach
    void tearDown() throws IOException {
        serverSocket.close();
        executorService.shutdownNow();
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @Test
    void testServerStart() throws IOException {
        // Simulate a client connecting to the server
        try (Socket clientSocket = new Socket("localhost", TEST_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // Read the server response
            String line1 = reader.readLine();
            String line2 = reader.readLine();

            // Verify file content is correctly sent to the client
            assertEquals("Hello", line1);
            assertEquals("World", line2);
        }
    }
}

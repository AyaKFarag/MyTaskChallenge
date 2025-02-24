package com.elisa.polystar.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextFileServer implements Server {

    private static final Logger logger = LoggerFactory.getLogger(TextFileServer.class);


    private final int port;
    private final String filePath;

    public TextFileServer(int port, String filePath) {
        this.port = port;
        this.filePath = filePath;
    }

    @Override
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started on port {}", port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected from {}", clientSocket.getInetAddress());

                // Handle each client in a new thread
                new Thread(() -> {
                    handleClient(clientSocket);
                }).start();
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }

        } catch (IOException e) {
            logger.error("Error handling client request: {}", e.getMessage(), e);
        }
    }
}

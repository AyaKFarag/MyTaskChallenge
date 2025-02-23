package com.elisa.polystar.servers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class DraculaServer implements Server {
    private static final Logger logger = LoggerFactory.getLogger(DraculaServer.class);
    private final int port;
    private final String fileName;

    public DraculaServer(int port, String fileName) {
        this.port = port;
        this.fileName = fileName;
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Dracula server is running on port {}", port);

            // Assuming serverStart is in another class, e.g., ServerHelper
            ServerHelper.serverStart(serverSocket, fileName);
        } catch (IOException e) {
            logger.error("Error in DraculaServer: {}", e.getMessage(), e);
        }
    }
}

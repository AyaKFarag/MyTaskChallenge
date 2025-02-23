package com.elisa.polystar.servers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class FrankensteinServer implements Server {
    private static final Logger logger = LoggerFactory.getLogger(FrankensteinServer.class);
    private final int port;
    private final String fileName;

    public FrankensteinServer(int port, String fileName) {
        this.port = port;
        this.fileName = fileName;
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Frankenstein server is running on port {}", port);
            ServerHelper.serverStart(serverSocket, fileName);
        } catch (IOException e) {
            logger.error("Error in FrankensteinServer: {}", e.getMessage(), e);
        }
    }
}

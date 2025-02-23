package com.elisa.polystar.servers;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FrankensteinServer implements Server {
    private final int port;
    private final String fileName;

    public FrankensteinServer(int port, String fileName) {
        this.port = port;
        this.fileName = fileName;
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Frankenstein server is running on port " + port);
            Server.serverStart(serverSocket, fileName);
        } catch (IOException e) {
            System.err.println("Error in FrankensteinServer: " + e.getMessage());
        }
    }
}

package com.elisa.polystar.servers;


import java.io.*;
import java.net.ServerSocket;

public class DraculaServer implements Server {
    private final int port;
    private final String fileName;

    public DraculaServer(int port, String fileName) {
        this.port = port;
        this.fileName = fileName;
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Dracula server is running on port " + port);
            Server.serverStart(serverSocket, fileName);
            return;
        } catch (IOException e) {
            System.err.println("Error in DraculaServer: " + e.getMessage());
        }
    }


}

package com.elisa.polystar.server;

import com.elisa.polystar.server.factory.FileServerFactory;

public class ServerRunner {

    public static void main(String[] args) {
        // Create servers using the factory
        Server draculaServer = FileServerFactory.createServer("dracula");
        Server frankensteinServer = FileServerFactory.createServer("frankenstein");

        // Start the servers using separate threads
        new Thread(() -> {
            try {
                draculaServer.start();
            } catch (Exception e) {
                System.err.println("Error starting Dracula server: " + e.getMessage());
            }
        }).start();

        new Thread(() -> {
            try {
                frankensteinServer.start();
            } catch (Exception e) {
                System.err.println("Error starting Frankenstein server: " + e.getMessage());
            }
        }).start();
    }
}

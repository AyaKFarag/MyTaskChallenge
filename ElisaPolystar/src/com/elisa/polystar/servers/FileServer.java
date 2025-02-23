package com.elisa.polystar.servers;

import com.elisa.polystar.factory.ServerFactory;

public class FileServer {

    public static void main(String[] args) {
        // Create servers using the factory
        Server draculaServer = ServerFactory.createServer("dracula", 6501);
        Server frankensteinServer = ServerFactory.createServer("frankenstein", 6500);

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

package com.elisa.polystar.servers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface Server {
    void start();
    static void serverStart(ServerSocket serverSocket, String fileName) throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected...");
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    out.println(line);
                }
            }
        }
    }
}

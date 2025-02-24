package com.elisa.polystar.server.factory;

import com.elisa.polystar.server.Server;
import com.elisa.polystar.server.TextFileServer;
import com.elisa.polystar.util.ConfigLoader;


public class FileServerFactory {
    public static Server createServer(String serverName) {
        int port = ConfigLoader.getIntProperty(serverName + ".port", -1);
        String filePath = ConfigLoader.getProperty(serverName + ".file", "");

        if (port == -1 || filePath.isEmpty()) {
            throw new IllegalArgumentException("Configuration for server: " + serverName + " is missing.");
        }

        return new TextFileServer(port, filePath);
    }
}
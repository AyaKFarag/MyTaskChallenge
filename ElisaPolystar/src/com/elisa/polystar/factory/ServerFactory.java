package com.elisa.polystar.factory;

import com.elisa.polystar.servers.DraculaServer;
import com.elisa.polystar.servers.FrankensteinServer;
import com.elisa.polystar.servers.Server;

public class ServerFactory {

    public static Server createServer(String fileName, int port) {
        if (fileName.equals("dracula")) {
            return new DraculaServer(port, "resources/dracula.txt");
        } else if (fileName.equals("frankenstein")) {
            return new FrankensteinServer(port, "resources/frankenstein.txt");
        }
        throw new IllegalArgumentException("Invalid file name: " + fileName);
    }
}

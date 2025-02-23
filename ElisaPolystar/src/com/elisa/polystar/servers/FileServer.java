package com.elisa.polystar.servers;

import com.elisa.polystar.factory.ServerFactory;

public class FileServer {

    public static void main(String[] args) {
        // Create servers using the factory
        Server draculaServer = ServerFactory.createServer("dracula", 6501);
        Server frankensteinServer = ServerFactory.createServer("frankenstein", 6500);

        // Start the servers
        new Thread(draculaServer::start).start();
        new Thread(frankensteinServer::start).start();
    }
}

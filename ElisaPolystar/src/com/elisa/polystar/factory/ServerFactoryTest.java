package com.elisa.polystar.factory;

import com.elisa.polystar.servers.DraculaServer;
import com.elisa.polystar.servers.FrankensteinServer;
import com.elisa.polystar.servers.Server;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServerFactoryTest {

    @Test
    void testCreateDraculaServer() {
        Server server = ServerFactory.createServer("dracula", 8080);
        assertNotNull(server);
        assertTrue(server instanceof DraculaServer);
    }

    @Test
    void testCreateFrankensteinServer() {
        Server server = ServerFactory.createServer("frankenstein", 9090);
        assertNotNull(server);
        assertTrue(server instanceof FrankensteinServer);
    }

    @Test
    void testCreateServerWithInvalidName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ServerFactory.createServer("unknown", 7070);
        });
        assertEquals("Invalid file name: unknown", exception.getMessage());
    }
}

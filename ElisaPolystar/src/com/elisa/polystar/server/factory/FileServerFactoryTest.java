package com.elisa.polystar.server.factory;

import com.elisa.polystar.server.Server;
import com.elisa.polystar.server.TextFileServer;
import com.elisa.polystar.util.ConfigLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileServerFactoryTest {

    private ConfigLoader config;

    @Test
    void testCreateServerFromConfig() {
        // Get the file paths from the config
        String draculaFilePath = ConfigLoader.getProperty("dracula.file","");
        String frankensteinFilePath = ConfigLoader.getProperty("dracula.file","");

        assertNotNull(draculaFilePath, "Dracula file path should be present in the config");
        assertNotNull(frankensteinFilePath, "Frankenstein file path should be present in the config");

        // Create the server instances using the configuration values
        Server draculaServer = FileServerFactory.createServer("dracula");
        assertNotNull(draculaServer);
        assertTrue(draculaServer instanceof TextFileServer, "Server should be of type TextFileServer");

        Server frankensteinServer = FileServerFactory.createServer("frankenstein");
        assertNotNull(frankensteinServer);
        assertTrue(frankensteinServer instanceof TextFileServer, "Server should be of type TextFileServer");
    }

    @Test
    void testCreateServerWithInvalidName() {
        // Test for an invalid server name from the config (should throw exception)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FileServerFactory.createServer("unknown");
        });
        assertEquals("Configuration for server: unknown is missing.", exception.getMessage());
    }

    @Test
    void testServerWithMissingFileMapping() {
        // Simulate missing file mapping in the config (should throw exception)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FileServerFactory.createServer("missing");
        });
        assertEquals("Configuration for server: missing is missing.", exception.getMessage());
    }
}

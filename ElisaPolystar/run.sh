#!/bin/bash
#set -x  # Enable debugging

# Ensure script runs from the project root
cd "$(dirname "$0")"

# Build the project, skipping unnecessary steps
echo "🔧 Building the project..."
mvn install -q -T 1C || { echo "❌ Build failed!"; exit 1; }

# Kill any processes using ports 6500 and 6501
echo "🛑 Checking and freeing up ports..."
lsof -ti:6500 | xargs kill -9 2>/dev/null
lsof -ti:6501 | xargs kill -9 2>/dev/null

# Run the FileServer class in the background and capture its PID
echo "Starting the FileServer..."
mvn exec:java -Dexec.mainClass="com.elisa.polystar.servers.FileServer" &
SERVER_PID=$!

# Wait for server to be fully ready (checking ports 6500 and 6501)
echo "⏳ Waiting for the server to start..."
until lsof -ti:6500 || lsof -ti:6501; do
    sleep 1
done

# Run client
echo "📊 Running client..."
mvn exec:java -Dexec.mainClass="com.elisa.polystar.client.WordCountClient"

# Stop the server after client fi

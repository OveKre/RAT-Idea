# Java Remote Administration Tool (RAT) - Proof of Concept

This is a simple, lightweight Remote Administration Tool (RAT) built in Java for **educational purposes and cybersecurity research**. It demonstrates the basic mechanics of network socket programming, command execution via Java runtime, and decoupling configuration data from the source code.

⚠️ **Disclaimer:** This project is created strictly for educational use, penetration testing labs, and understanding how reverse connections work. Do not use this software on any devices without explicit prior authorization.

## Features
- **Client-Server Architecture:** Built using native Java Sockets.
- **Remote Command Execution:** Allows executing terminal/shell commands on the host machine.
- **External Configuration:** IP addresses and ports are safely managed outside the code using a properties file.

## Project Structure
- `Server.java` - Runs on the host machine (victim/target), waits for an incoming connection, and executes received commands.
- `Client.java` - The control console used to send terminal commands to the server.
- `config.properties.example` - A template file for configuring network options without leaking local IP addresses to GitHub.

## How to Setup and Run

### 1. Configuration
To prevent sensitive data leaks, the actual `config.properties` file is ignored by Git. To configure your connection:
1. Copy the example config file:
   ```bash
   cp config.properties.example config.properties
   ```
2. Open `config.properties` and replace the IP address with your server's local or public network IP:
   ```properties
   server.ip=your.ip.address
   server.port=port.number
   ```

### 2. Running the Server (Dockerized)
The server can be safely isolated inside a Docker container:
```bash
docker build -t java-rat-server .
docker run -d -p xxxx:xxxx --name rat_server java-rat-server
```

### 3. Compiling and Running the Client
Compile the client file and run it alongside your `config.properties` file:
```bash
javac Client.java
java Client
```

Once connected, you will see the console prompt: `Java_RAT_console $>`. Type `exit` to close the connection.

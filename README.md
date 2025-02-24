**# Elisa Polystar Project**

The **Polystar Project** is a Java-based **server-client application** that enables text-based communication between a **Generic File Server** and a **Word Count Client**. The project leverages key software principles and technologies, including:

- **Factory Design Pattern** for modular and scalable server creation.
- **Configuration Files** for dynamic server setup (file paths and ports).
- **SLF4J Logging** for better monitoring and debugging.
- **Maven** for dependency management and build automation.

### **Project Overview**
- Dynamically creates servers **Text File Server** based on configuration files.
- The **Word Count Client** processes received data to determine the most frequently used words.
- Uses a ConfigLoader to load file paths and other configurations.
  Supports different server types, such as TextFileServer.
- ServerRunner is responsible for running the server and handling communication between the client and server.
- The system is **configurable, scalable, and extendable**, allowing easy integration of new features and server types.


## **Design Rationale**
The Polystar Project follows key software design principles to ensure **scalability, flexibility, and maintainability**:

- **Factory Design Pattern:**
    - Decouples server creation logic.
    - Allows adding new server types without modifying existing code.
- **SLF4J Logging:**
    - Provides a standardized logging approach.
    - Enhances debugging and monitoring capabilities.
- **Separation of Concerns:**
    - Keeps server and client logic independent.
    - Improves modularity and maintainability.
- **Maven for Build Automation:**
    - Simplifies dependency management.
    - Supports integration with CI/CD pipelines.

This structured approach enhances **adaptability and long-term maintainability**, ensuring the system remains **extensible** as requirements evolve.

## **Getting Started**
### **Prerequisites**
Before running the project, ensure you have:
- **Java 11** or higher
- **Maven** installed
- **Git** installed

### **Cloning the Repository**
Clone the project to your local machine:
```sh
git clone https://github.com/AyaKFarag/ElisaPolystar.git
cd ElisaPolystar
```

### **Building and Running the Project**
To build and run the project, use the `run.sh` script:
```sh
./run.sh
```
This script will:
1. **Build the project**
2. **Start the file server**
3. **Run the client to interact with the server**

## Code Structure
**com.elisa.polystar.server:**
- Contains the main server and file server classes.
- TextFileServer.java: A server that serves text files.
- FileServerFactory.java: A factory class to create servers based on configuration values.
- ServerRunner.java: A class responsible for running the server and managing client-server communication.
**com.elisa.polystar.util:** Contains utility classes.
- ConfigLoader.java: Loads configuration from a properties file and provides methods to retrieve configuration values.


## **Future Improvements**
The project has potential for significant enhancements, including:

### **Security Enhancements:**
- Implement authentication and encryption.
- Add input validation for better security.

### **Additional Features:**
- Improve file handling and error management.
- Develop a user-friendly UI.

### **Testing & CI/CD:**
- Increase unit and integration test coverage.
- Implement automated builds and deployment pipelines.

### **Scalability:**
- Dockerize the application.
- Use Kubernetes for orchestration.
- Implement load balancing.

### **Monitoring & Logging:**
- Centralized logging for better debugging.
- Real-time health checks and performance monitoring.

### **Documentation:**
- Enhance API and user documentation for better usability.

By focusing on these improvements, the **Polystar Project** aims to evolve into a **secure, scalable, and feature-rich application** with **long-term maintainability**. 🚀


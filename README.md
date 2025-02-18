# Ticket Management System - GUI

A Java-based desktop application that simulates a ticket management system using JavaFX. This project demonstrates multi-threading, a configurable ticket pool, and real-time logging.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Folder Structure](#folder-structure)
- [Dependencies](#dependencies)
- [Prerequisites](#prerequisites)
- [Installation and Running](#installation-and-running)
- [Usage](#usage)
- [Configuration](#configuration)
- [Contributing](#contributing)

## Overview

The Ticket Management System is a desktop application built with Java and JavaFX. It simulates a ticket management process where:

- A **Vendor** thread generates tickets and adds them to a synchronized ticket pool.
- A **Customer** thread retrieves tickets from the pool.
- The application uses a JSON configuration file to manage system parameters like total tickets, ticket release rate, customer retrieval rate, and maximum ticket capacity.
- A logging mechanism records and displays system events in real time.

## Features

- **JavaFX GUI:** An intuitive user interface for setting ticket parameters, viewing logs, and monitoring the ticket pool.
- **Multi-threading:** Concurrent execution of ticket production (Vendor) and consumption (Customer).
- **Configuration Management:** JSON-based configuration for dynamic system parameter adjustments.
- **Real-Time Logging:** Logs key events to both the console and a dedicated log file.
- **Modular Architecture:** Clear separation of concerns across different packages.

## Folder Structure
```
TicketManagementSystem/
├── src/
│   ├── main/
│   │   └── Main.java           # Application entry point.
│   ├── config/
│   │   └── Configuration.java  # Manages configuration settings via JSON.
│   ├── core/
│   │   ├── AbstractTicketHandler.java  # Abstract class for ticket handling.
│   │   └── TicketPool.java     # Thread-safe ticket pool.
│   ├── threads/
│   │   ├── Vendor.java         # Generates and adds tickets.
│   │   └── Customer.java       # Retrieves tickets.
│   ├── logging/
│   │   └── Logger.java         # Handles system logging.
│   ├── ui/
│   │   └── JavaFXInterface.java # JavaFX-based user interface.
│   ├── resources/
│   │   └── style.css               # CSS for the UI.
│   └── module-info.java        # Module configuration.
└── resources/
    └── logs.txt                # Log file output.

```


## Dependencies

The project requires the following libraries:

- **gson-2.8.9.jar:** For JSON parsing and configuration management.
- **JavaFX Libraries:**
  - `javafx.base.jar`
  - `javafx.controls.jar`
  - `javafx.fxml.jar`
  - `javafx.graphics.jar`
  - `javafx.media.jar`
  - `javafx.swing.jar`
  - `javafx.web.jar`
  - `javafx-swt.jar`

## Prerequisites

- **Java Development Kit (JDK):** Version 11 or higher.
- **JavaFX SDK:** Make sure the JavaFX SDK is installed and configured. You can download it from [Gluon](https://gluonhq.com/products/javafx/).
- **Build Tool or IDE:** Use an IDE like IntelliJ IDEA or Eclipse, or a build tool like Maven/Gradle configured with the above dependencies.

## Installation and Running

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/R-Tharanka/ticket-management-s-gui.git
   cd ticket-management-s-gui
   ```

2. **Configure the Project**

    Import the project into your IDE or set up your build tool. Ensure that both the JavaFX SDK and the Gson library are added to your project dependencies.

3. **Build the Project**

    Compile the project using your preferred method (IDE or build tool).

4. **Run the Application**

    Execute the `Main.java` file. From the command line, you can run:

    ```bash
    java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -jar target/your-app.jar
    ```
Adjust the command based on your environment and build configuration.

## Usage

### Set Parameters
  Enter values for Total Tickets, Ticket Release Rate, Customer Retrieval Rate, and Maximum Ticket Capacity in the GUI input fields.

### Start the System
  Click the **Start** button to begin the simulation. The Vendor thread will start generating tickets while the Customer thread retrieves them. The application displays system logs and the current state of the ticket pool in real time.

### Stop the System
  Click the **Stop** button to safely interrupt the threads and halt the simulation.

## Configuration
  The application uses a JSON configuration file to manage system settings. The `Configuration.java` class handles loading and saving these settings. Modify the configuration file as needed to adjust simulation parameters.

## Contributing  
```sh
# Fork the repository
git clone https://github.com/R-Tharanka/ticket-management-s-gui.git

# Create a new branch
git checkout -b feature-branch

# Commit your changes
git commit -m "Added new feature"

# Push to GitHub
git push origin feature-branch

# Submit a Pull Request
```

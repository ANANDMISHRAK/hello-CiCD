package com.example.hellocicd; // Declares the package; the folder path must match this package name.

import org.springframework.boot.SpringApplication; // Provides the method that starts the Spring Boot application.
import org.springframework.boot.autoconfigure.SpringBootApplication; // Imports the annotation that enables Boot configuration and component scanning.

/**
 * Main entry point for the Spring Boot application.
 * Run this class to start the embedded web server and register the REST controller.
 */
@SpringBootApplication // Combines configuration, auto-configuration and component scanning.
public class HelloCicdApplication { // Declares the main application class.

    public static void main(String[] args) { // Java starts execution from this main method.
        SpringApplication.run(HelloCicdApplication.class, args); // Starts Spring, creates beans and launches the embedded server.
    } // Ends the main method.
} // Ends the application class.

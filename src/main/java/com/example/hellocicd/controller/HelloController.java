package com.example.hellocicd.controller; // Declares the controller package.

import org.springframework.web.bind.annotation.GetMapping; // Maps HTTP GET requests to a Java method.
import org.springframework.web.bind.annotation.RestController; // Marks this class as a REST controller whose return value becomes the response body.

/**
 * Exposes the sample hello endpoint.
 * Keeping web endpoints in a controller package makes the project easier to understand and test.
 */
@RestController // Tells Spring to detect this class and write method return values directly to HTTP responses.
public class HelloController { // Declares the controller class.

    @GetMapping("/getHello") // Handles GET requests sent to /getHello.
    public String getHello() { // Defines the Java method that Spring calls for this route.
        return "Hello from Spring Boot!"; // Sends this text to the client with HTTP 200 when the method succeeds.
    } // Ends the endpoint method.
} // Ends the controller class.

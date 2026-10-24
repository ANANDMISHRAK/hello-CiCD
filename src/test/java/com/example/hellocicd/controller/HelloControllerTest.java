package com.example.hellocicd.controller; // Places the test alongside the controller's package structure.

import org.junit.jupiter.api.Test; // Marks a method as a JUnit 5 test.
import org.springframework.beans.factory.annotation.Autowired; // Lets Spring inject the test helper.
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // Starts a focused MVC test slice without starting the whole application.
import org.springframework.test.web.servlet.MockMvc; // Sends simulated HTTP requests to Spring MVC without a real network port.

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Creates a simulated HTTP GET request.
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // Provides assertions for response content.
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Provides assertions for HTTP status codes.

/**
 * Tests the API contract: route, HTTP status and response body.
 * A failing assertion makes Maven test fail, which then makes CI fail.
 */
@WebMvcTest(HelloController.class) // Loads MVC components needed for HelloController rather than every application component.
class HelloControllerTest { // Declares the test class; package-private is supported by JUnit 5.

    @Autowired // Asks Spring to inject the configured MockMvc instance.
    private MockMvc mockMvc; // Holds the tool used to exercise the controller through Spring MVC.

    @Test // Tells JUnit to execute this method as a test.
    void getHelloReturnsExpectedMessage() throws Exception { // Describes the expected behavior and allows request/assertion exceptions.
        mockMvc.perform(get("/getHello")) // Simulates a GET request to the endpoint.
                .andExpect(status().isOk()) // Verifies that the server responds with HTTP 200 OK.
                .andExpect(content().string("Hello from Spring Boot!")); // Verifies the exact response text.
    } // Ends the test method.
} // Ends the test class.

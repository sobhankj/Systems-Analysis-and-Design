package com.example.demo;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private final HandelBuffer handelBuffer;

    public MessageListener(HandelBuffer handelBuffer) {
        this.handelBuffer = handelBuffer;
    }

    @JmsListener(destination = "INQ")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        // Process the received message and obtain a response
        String response = handelBuffer.handleMessage(message);
        // Send the response message to the OUTQ queue
        handelBuffer.respondToQueue(response);
    }
}
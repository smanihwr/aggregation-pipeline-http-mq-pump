package com.example.httpmqpump.controller;

import com.example.httpmqpump.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(Source.class)
@RequestMapping("/message-intake/api")
public class MessageInboundController {

    @Autowired
    private Source source;

    @PostMapping(path = "/message", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> insertMessage(@RequestBody Message message) {
        System.out.println("Received message: " + message);

        source.output().send(MessageBuilder.withPayload(message).build());

        return ResponseEntity.ok().body(message);
    }

}


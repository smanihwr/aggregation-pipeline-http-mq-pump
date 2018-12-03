package com.example.httpmqpump.controller;

import com.example.httpmqpump.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(MessageInboundController.class);

    @Autowired
    private Source source;

    @PostMapping(path = "/message", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> insertMessage(@RequestBody Message message) {
        boolean result = source.output().send(MessageBuilder.withPayload(message).build());
        LOG.debug("Sent message: " + message +" : " + result);
        return ResponseEntity.ok().body(message);
    }

}


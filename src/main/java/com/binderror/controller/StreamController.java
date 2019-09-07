package com.binderror.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamController {

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @RequestMapping(value = "/pause/{containerId}", method = RequestMethod.GET)
    public Boolean pauseContainer(@PathVariable final String containerId) {
        registry.getListenerContainer(containerId).pause();
        return Boolean.TRUE;
    }

    @RequestMapping(value = "/resume/{containerId}", method = RequestMethod.GET)
    public Boolean resumeContainer(@PathVariable final String containerId) {
        registry.getListenerContainer(containerId).resume();
        return Boolean.TRUE;
    }
}

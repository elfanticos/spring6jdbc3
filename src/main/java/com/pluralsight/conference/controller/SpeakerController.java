package com.pluralsight.conference.controller;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.service.SpeakerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpeakerController {

    private static final Logger log = LoggerFactory.getLogger(SpeakerController.class);
    private final SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @PutMapping
    public Speaker createSpeaker(@RequestBody Speaker speaker) {
        SpeakerController.log.info("Name: {}", speaker.getName());
        return speakerService.create(speaker);
    }

    @GetMapping
    public List<Speaker> getSpeakers() {
        return speakerService.findAll();
    }
}

package com.pluralsight.conference.controller;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.service.SpeakerService;
import com.pluralsight.conference.util.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/speaker")
public class SpeakerController {

    private static final Logger log = LoggerFactory.getLogger(SpeakerController.class);
    private final SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @PostMapping
    public Speaker createSpeaker(@RequestBody Speaker speaker) {
        SpeakerController.log.info("Name: {}", speaker.getName());
        return speakerService.create(speaker);
    }

    @GetMapping
    public List<Speaker> getSpeakers() {
        return speakerService.findAll();
    }

    @GetMapping("{id}")
    public Speaker getSpeaker(@PathVariable("id") int id) {
        return speakerService.getSpeaker(id);
    }

    @PutMapping
    public Speaker updateSpeaker(@RequestBody Speaker speaker) {
        return speakerService.update(speaker);
    }

    @GetMapping("/batch")
    public Object batch() {
        speakerService.batch();
        return null;
    }

    @DeleteMapping("{id}")
    public Object deleteSpeaker(@PathVariable("id") int id) {
        speakerService.delete(id);
        return null;
    }

    @GetMapping("test")
    public Object test() {
        throw new DataAccessException("Testing Exception Thrown") {
        };
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ServiceError> handler(RuntimeException ex) {
        ServiceError serviceError = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
        return new ResponseEntity<>(serviceError, HttpStatus.OK);
    }
}

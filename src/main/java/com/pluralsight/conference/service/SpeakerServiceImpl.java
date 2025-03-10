package com.pluralsight.conference.service;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.repository.SpeakerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("speakerService")
public class SpeakerServiceImpl implements SpeakerService {

    private final SpeakerRepository speakerRepository;

    public SpeakerServiceImpl(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    @Override
    public List<Speaker> findAll() {
        return speakerRepository.findAll();
    }

    @Override
    public Speaker create(Speaker speaker) {
        return speakerRepository.create(speaker);
    }

    @Override
    public Speaker getSpeaker(int id) {
        return speakerRepository.getSpeaker(id);
    }

    @Override
    public Speaker update(Speaker speaker) {
        return speakerRepository.update(speaker);
    }

    @Override
    public void batch() {
        List<Speaker> speakers = speakerRepository.findAll();

        List<Object[]> pairs = new ArrayList<>();

        for (Speaker speaker : speakers) {
            pairs.add(new Object[]{"Java", speaker.getId()});
        }

        speakerRepository.update(pairs);

    }

    @Override
    public Object delete(int id) {
        return speakerRepository.delete(id);
    }
}

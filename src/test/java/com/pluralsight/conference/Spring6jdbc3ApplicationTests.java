package com.pluralsight.conference;

import com.pluralsight.conference.model.Speaker;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootTest()
class Spring6jdbc3ApplicationTests {
    private final Logger log = LoggerFactory.getLogger(Spring6jdbc3ApplicationTests.class);

    @Test
    void testCreateSpeaker() {
        RestTemplate restTemplate = new RestTemplate();

        Speaker speaker = new Speaker();
        speaker.setName("Janet Taxa");

        restTemplate.postForEntity("http://localhost:8080/speaker", speaker, Speaker.class);

        assertTrue(true, "speaker is null");
    }

    @Test
    void testGetSpeakers() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Speaker>> speakersResponse = restTemplate.exchange(
                "http://localhost:8080/speaker", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Speaker>>() {
                });

        assertNotNull(speakersResponse.getBody(), "Body is null");

        List<Speaker> speakers = speakersResponse.getBody();

        for (Speaker speaker : speakers) {
            log.info("Speaker: {}", speaker);
        }
    }

    @Test
    void testGetSpeaker() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Speaker> speakerResponse = restTemplate.exchange(
                "http://localhost:8080/speaker/6", HttpMethod.GET,
                null, new ParameterizedTypeReference<Speaker>() {
                });

        assertNotNull(speakerResponse.getBody(), "Body is null");

        Speaker speaker = speakerResponse.getBody();

        log.info("Speaker: {}", speaker);
    }

    @Test
    void testUpdateSpeaker() {
        RestTemplate restTemplate = new RestTemplate();

        Speaker speaker = restTemplate.getForObject("http://localhost:8080/speaker/{id}", Speaker.class, 17);
        assertNotNull(speaker, "Speaker is null");

        speaker.setName("Giovanni Franchi");

        restTemplate.put("http://localhost:8080/speaker", speaker);
        log.info("Speaker modify name: {}, id: {}", speaker.getName(), speaker.getId());
    }

    @Test
    void testBatchUpdate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://localhost:8080/speaker/batch", Object.class);
    }

    @Test
    void testDeleteSpeaker() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8080/speaker/{id}", 6);
    }

    @Test
    void testException() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("http://localhost:8080/speaker/test", Speaker.class);
    }
}

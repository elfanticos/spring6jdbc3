package com.pluralsight.conference.repository;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.repository.util.SpeakerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository("speakerRepository")
public class SpeakerRepositoryImpl implements SpeakerRepository {
    private final JdbcTemplate jdbcTemplate;

    public SpeakerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Speaker> findAll() {
        return jdbcTemplate.query("""
                SELECT * FROM speaker
                """, new SpeakerRowMapper());
    }

    @Override
    public Speaker create(Speaker speaker) {
//        jdbcTemplate.update("INSERT INTO speaker(name) VALUES (?)", speaker.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("INSERT INTO speaker(name) VALUES (?)", new String[]{"id"});
                ps.setString(1, speaker.getName());
                return ps;
            }
        }, keyHolder);

        Number id = keyHolder.getKey();

        return getSpeaker(Objects.requireNonNull(id).intValue());
    }

    @Override
    public Speaker getSpeaker(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM speaker WHERE id = ?", new SpeakerRowMapper(), id);
    }

    @Override
    public Speaker update(Speaker speaker) {
        return jdbcTemplate.update("UPDATE speaker SET name = ? WHERE id = ?", speaker.getName(), speaker.getId()) > 0 ? speaker : null;
    }

    @Override
    public void update(List<Object[]> pairs) {
        jdbcTemplate.batchUpdate("UPDATE speaker SET skill = ? WHERE id = ?", pairs);
    }

    @Override
    public Object delete(int id) {
//        jdbcTemplate.update("DELETE FROM speaker WHERE id = ?", id);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        namedParameterJdbcTemplate.update("DELETE FROM speaker WHERE id = :id", params);
        return null;
    }
}

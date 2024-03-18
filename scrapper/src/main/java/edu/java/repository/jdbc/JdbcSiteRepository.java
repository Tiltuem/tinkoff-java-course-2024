package edu.java.repository.jdbc;

import edu.java.exception.SiteAlreadyExistsException;
import edu.java.exception.SiteNotFoundException;
import edu.java.model.Site;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSiteRepository {
    private static final String SAVE = "INSERT INTO sites(site_name) VALUES (?)";
    private static final String REMOVE = "DELETE FROM sites WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM sites";
    private static final String FIND_BY_NAME = "SELECT * FROM sites WHERE site_name = ?";
    private static final BeanPropertyRowMapper<Site> MAPPER = new BeanPropertyRowMapper<>(Site.class);
    @Autowired
    private JdbcClient jdbcClient;

    public void save(String name) {
        try {
            jdbcClient.sql(SAVE).param(name).update();
        } catch (DataIntegrityViolationException e) {
            throw new SiteAlreadyExistsException("Site with URL = %s already exists".formatted(name));
        }
    }

    public void remove(Long id) {
        if (jdbcClient.sql(REMOVE).param(id).update() == 0) {
            throw new SiteNotFoundException("Site not found.");
        }
    }

    public List<Site> findAll() {
        return jdbcClient.sql(FIND_ALL).query(MAPPER).list();
    }

    public Optional<Site> findByName(String name) {
        try {
            return Optional.of(jdbcClient.sql(FIND_BY_NAME).param(name).query(MAPPER).single());
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }
}

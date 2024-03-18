package edu.java.repository.jooq.repository;

import edu.java.exception.SiteAlreadyExistsException;
import edu.java.exception.SiteNotFoundException;
import edu.java.model.Site;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import static edu.java.repository.jooq.Tables.SITES;

@Repository
@RequiredArgsConstructor
public class JooqSiteRepository {
    private final DSLContext dslContext;

    public void save(String name) {
        try {
            dslContext
                .insertInto(SITES)
                .set(SITES.SITE_NAME, name)
                .execute();
        } catch (DataAccessException e) {
            throw new SiteAlreadyExistsException("Site with URL = %s already exists".formatted(name));
        }
    }

    public void remove(Long id) {
        int rowsAffected = dslContext
            .deleteFrom(SITES)
            .where(SITES.ID.eq(id))
            .execute();
        if (rowsAffected == 0) {
            throw new SiteNotFoundException("Site not found.");
        }
    }

    public List<Site> findAll() {
        return dslContext
            .selectFrom(SITES)
            .fetch()
            .map(rec -> new Site(rec.getId(), rec.getSiteName()));
    }

    public Optional<Site> findByName(String name) {
        return dslContext
            .selectFrom(SITES)
            .where(SITES.SITE_NAME.eq(name))
            .fetchOptional()
            .map(rec -> new Site(rec.getId(), rec.getSiteName()));
    }
}

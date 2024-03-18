package edu.java.repository.jooq.repository;

import edu.java.exception.UserAlreadyRegisteredException;
import edu.java.exception.UserIsNotRegisteredException;
import edu.java.model.User;
import edu.java.repository.jooq.tables.records.UserLinksRecord;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;
import static edu.java.repository.jooq.Tables.USERS;
import static edu.java.repository.jooq.Tables.USER_LINKS;

@Repository
@RequiredArgsConstructor
public class JooqUserRepository {
    private final DSLContext dslContext;

    public void save(Long chatId) {
        try {
            dslContext
                .insertInto(USERS)
                .set(USERS.CHAT_ID, chatId)
                .execute();
        } catch (DataAccessException e) {
            throw new UserAlreadyRegisteredException("User with chatId = %d already exists".formatted(chatId));
        }
    }


    public void remove(Long id) {
        int rowsAffected = dslContext
            .deleteFrom(USERS)
            .where(USERS.ID.eq(id))
            .execute();
        if (rowsAffected == 0) {
            throw new UserIsNotRegisteredException("User not found.");
        }
    }

    public List<User> findAll() {
        return dslContext
            .selectFrom(USERS)
            .fetch()
            .map(rec -> new User(rec.getId(), rec.getChatId()));
    }

    public Optional<User> findById(Long id) {
        return dslContext
            .selectFrom(USERS)
            .where(USERS.ID.eq(id))
            .fetchOptional()
            .map(rec -> new User(rec.getId(), rec.getChatId()));
    }

    public Optional<User> findByChatId(Long chatId) {
        return dslContext
            .selectFrom(USERS)
            .where(USERS.CHAT_ID.eq(chatId))
            .fetchOptional()
            .map(rec -> new User(rec.getId(), rec.getChatId()));
    }

    public List<Long> findUsersTrackLink(Long linkId) {
        return dslContext
            .selectFrom(USER_LINKS)
            .where(USER_LINKS.LINK_ID.eq(linkId))
            .fetchStream()
            .map(UserLinksRecord::getUserId)
            .collect(Collectors.toList());
    }
}

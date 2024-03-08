--liquibase formatted sql

--changeset tony:id1
CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT UNIQUE NOT NULL
);
--rollback drop table users;

--changeset tony:id3
CREATE TABLE IF NOT EXISTS sites(
    id BIGSERIAL PRIMARY KEY,
    name BIGINT UNIQUE NOT NULL
);
--rollback drop table sites;

--changeset tony:id2
CREATE TABLE IF NOT EXISTS links(
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    last_update TIMESTAMP,
    site_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY("site_id") REFERENCES "sites"("id"),
    FOREIGN KEY("user_id") REFERENCES "users"("id")
);
--rollback drop table links;

--changeset tony:for_testing
INSERT INTO users(chat_id)
VALUES (1), (2), (3);

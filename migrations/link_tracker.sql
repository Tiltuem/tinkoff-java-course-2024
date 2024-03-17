--liquibase formatted sql

--changeset tony:id1
CREATE TABLE IF NOT EXISTS users(
    id      BIGSERIAL PRIMARY KEY,
    chat_id BIGINT UNIQUE NOT NULL
);
--rollback drop table users;

--changeset tony:id2
CREATE TABLE IF NOT EXISTS sites(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);
--rollback drop table sites;

--changeset tony:id3
CREATE TABLE IF NOT EXISTS links(
    id          BIGSERIAL PRIMARY KEY,
    url         VARCHAR(255) UNIQUE NOT NULL,
    last_update TIMESTAMP,
    site_id     BIGINT              NOT NULL,
    last_check  TIMESTAMP,
    FOREIGN KEY ("site_id") REFERENCES "sites" ("id")
);
--rollback drop table links;

--changeset tony:id4
CREATE TABLE IF NOT EXISTS user_links(
    user_id BIGINT REFERENCES users (id),
    link_id BIGINT REFERENCES links (id),
    PRIMARY KEY (user_id, link_id)
);
--rollback drop table user_links;

--changeset tony:for_testing
INSERT INTO users(chat_id)
VALUES (1),
       (2),
       (3);

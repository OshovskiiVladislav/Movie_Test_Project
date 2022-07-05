CREATE TABLE movies
(
    id           BIGSERIAL,
    kinopoisk_id VARCHAR(255) UNIQUE NOT NULL,
    title        VARCHAR(255)        NOT NULL,
    poster_path  VARCHAR(255)        NOT NULL,
    PRIMARY KEY (id)
);

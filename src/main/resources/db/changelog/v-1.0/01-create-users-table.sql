CREATE TABLE users
(
    id       BIGSERIAL,
    username VARCHAR(80) UNIQUE NOT NULL,
    name     VARCHAR(255),
    password VARCHAR(80)        NOT NULL,
    email    VARCHAR(255) UNIQUE,
    PRIMARY KEY (id)
);

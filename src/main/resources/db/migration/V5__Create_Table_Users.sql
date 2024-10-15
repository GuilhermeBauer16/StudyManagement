CREATE TABLE users
(
    id          char(36)            NOT NULL,
    name        VARCHAR(255)        NOT NULL,
    email       VARCHAR(255) UNIQUE NOT NULL,
    password    VARCHAR(255)        NOT NULL,
    roles VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

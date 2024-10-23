CREATE TABLE roles
(
    id          char(36)            NOT NULL,
    role_name   VARCHAR(50),
    description VARCHAR(100),
    id_user char(36),
    PRIMARY KEY(id),
    CONSTRAINT fk_customer FOREIGN KEY (id_user) REFERENCES users (id)
);
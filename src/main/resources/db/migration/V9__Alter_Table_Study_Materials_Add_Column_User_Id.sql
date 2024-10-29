ALTER TABLE study_materials
    ADD COLUMN user_id CHAR(36)  NOT NULL,
    ADD FOREIGN KEY (user_id) REFERENCES users(id);

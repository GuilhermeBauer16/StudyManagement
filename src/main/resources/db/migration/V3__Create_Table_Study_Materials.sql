CREATE TABLE study_materials (
    id CHAR(36) NOT NULL,
    title VARCHAR(255),
    content TEXT,
    course_id CHAR(36),
    links_id CHAR(36),
    PRIMARY KEY(id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (links_id) REFERENCES links(id)
);



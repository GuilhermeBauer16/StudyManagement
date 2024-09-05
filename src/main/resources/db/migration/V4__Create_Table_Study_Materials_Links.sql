CREATE TABLE study_materials_links (
    study_material_id CHAR(36) NOT NULL,
    link_id CHAR(36) NOT NULL,
    PRIMARY KEY (study_material_id, link_id),
    FOREIGN KEY (study_material_id) REFERENCES study_materials(id),
    FOREIGN KEY (link_id) REFERENCES links(id)
);
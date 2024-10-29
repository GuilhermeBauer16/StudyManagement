package com.github.guilhermebauer.studymanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "study_materials")
public class StudyMaterialEntity {

    @Id
    private String id;
    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    @ManyToMany(fetch = FetchType.EAGER, cascade = ALL)
    @JoinTable(
            name = "study_materials_links",
            joinColumns = @JoinColumn(name = "study_material_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id")
    )
    private List<LinkEntity> links;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    public StudyMaterialEntity() {
    }


    public StudyMaterialEntity(String id, String title, String content, CourseEntity courseEntity, List<LinkEntity> links, UserEntity userEntity) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.courseEntity = courseEntity;
        this.links = links;
        this.userEntity = userEntity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CourseEntity getCourseEntity() {
        return courseEntity;
    }

    public void setCourseEntity(CourseEntity courseEntity) {
        this.courseEntity = courseEntity;
    }

    public List<LinkEntity> getLinks() {
        return links;
    }

    public void setLinks(List<LinkEntity> links) {
        this.links = links;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}

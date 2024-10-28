package com.github.guilhermebauer.studymanagement.model.values;

import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.UserEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.List;

public class StudyMaterialVO {

    private String id;
    private String title;
    private String content;
    private CourseEntity courseEntity;
    private List<LinkEntity> links;
    private UserEntity userEntity;

    public StudyMaterialVO() {
    }


    public StudyMaterialVO(String id, String title, String content, CourseEntity courseEntity, List<LinkEntity> links, UserEntity userEntity) {
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

package com.github.guilhermebauer.studymanagement.model.values;

import com.github.guilhermebauer.studymanagement.model.UserEntity;

public class CourseVO {

    private String id;
    private String title;
    private String description;
    private UserEntity userEntity;

    public CourseVO() {
    }

    public CourseVO(String id, String title, String description, UserEntity userEntity) {
        this.id = id;
        this.title = title;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}

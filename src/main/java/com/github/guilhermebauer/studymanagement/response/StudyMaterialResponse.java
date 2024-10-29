package com.github.guilhermebauer.studymanagement.response;

import com.github.guilhermebauer.studymanagement.model.LinkEntity;

import java.util.List;

public class StudyMaterialResponse {

    private String id;
    private String title;
    private String content;
    private CourseResponse courseResponse;
    private List<LinkEntity> links;

    public StudyMaterialResponse() {
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

    public CourseResponse getCourseResponse() {
        return courseResponse;
    }

    public void setCourseResponse(CourseResponse courseResponse) {
        this.courseResponse = courseResponse;
    }

    public List<LinkEntity> getLinks() {
        return links;
    }

    public void setLinks(List<LinkEntity> links) {
        this.links = links;
    }
}

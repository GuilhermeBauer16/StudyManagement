package com.github.guilhermebauer.studymanagement.response;

public class StudyMaterialUpdateResponse {

    private String id;
    private String title;
    private String content;

    public StudyMaterialUpdateResponse() {
    }

    public StudyMaterialUpdateResponse(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
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
}


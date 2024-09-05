package com.github.guilhermebauer.studymanagement.model.values;

public class LinkVO {

    private String id;
    private String url;
    private String description;

    public LinkVO() {
    }

    public LinkVO(String id, String url, String description) {
        this.id = id;
        this.url = url;
        this.description = description;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

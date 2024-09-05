package com.github.guilhermebauer.studymanagement.request;

import com.github.guilhermebauer.studymanagement.model.LinkEntity;

import java.util.List;

public class LinkListToStudyMaterialRequest {

    private String id;
    private List<LinkEntity> links;

    public LinkListToStudyMaterialRequest(String id, List<LinkEntity> links) {
        this.id = id;
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LinkEntity> getLinks() {
        return links;
    }

    public void setLinks(List<LinkEntity> links) {
        this.links = links;
    }
}

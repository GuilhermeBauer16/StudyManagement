package com.github.guilhermebauer.studymanagement.request;

import com.github.guilhermebauer.studymanagement.model.values.LinkVO;

public class SingleLinkToStudyMaterialRequest {

    private String id;
    private LinkVO link;

    public SingleLinkToStudyMaterialRequest(String id, LinkVO link) {
        this.id = id;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkVO getLink() {
        return link;
    }

    public void setLink(LinkVO link) {
        this.link = link;
    }
}

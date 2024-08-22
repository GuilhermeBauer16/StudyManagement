package com.github.guilhermebauer.studymanagement.request;

import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;

import java.util.List;

public class StudyMaterialRequest {

    private StudyMaterialEntity studyMaterial;
    private List<LinkEntity> links;

    // Getters and Setters

    public StudyMaterialEntity getStudyMaterial() {
        return studyMaterial;
    }

    public void setStudyMaterial(StudyMaterialEntity studyMaterial) {
        this.studyMaterial = studyMaterial;
    }

    public List<LinkEntity> getLinks() {
        return links;
    }

    public void setLinks(List<LinkEntity> links) {
        this.links = links;
    }
}

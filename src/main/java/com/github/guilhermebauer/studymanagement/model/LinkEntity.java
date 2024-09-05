package com.github.guilhermebauer.studymanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "links")
public class LinkEntity {

    @Id
    private String id;
    private String url;
    private String description;


    public LinkEntity() {
    }

    public LinkEntity(String id, String url, String description ) {
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

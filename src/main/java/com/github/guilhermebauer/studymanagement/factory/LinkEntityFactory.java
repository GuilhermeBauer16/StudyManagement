package com.github.guilhermebauer.studymanagement.factory;

import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.utils.UuidUtils;

public class LinkEntityFactory {

    public LinkEntityFactory() {
    }

    public static LinkEntity create(String url, String description){
        return new LinkEntity(UuidUtils.generateUuid(),url,description);
    }



}

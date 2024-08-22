package com.github.guilhermebauer.studymanagement.service.contract;

import com.github.guilhermebauer.studymanagement.model.LinkEntity;

import java.util.List;

public interface LinkServiceContract {

    List<LinkEntity> create(List<LinkEntity> linkEntity);
}

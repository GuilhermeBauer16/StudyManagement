package com.github.guilhermebauer.studymanagement.service.contract;


import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LinkServiceContract {

    List<LinkEntity> create(List<LinkEntity> linkEntities) throws IllegalAccessException;

    LinkVO update(com.github.guilhermebauer.studymanagement.model.values.LinkVO linkVO) throws NoSuchFieldException, IllegalAccessException;

    LinkVO findLinkById(String id) throws NoSuchFieldException, IllegalAccessException;

    @Transactional
    void delete(String id);

}

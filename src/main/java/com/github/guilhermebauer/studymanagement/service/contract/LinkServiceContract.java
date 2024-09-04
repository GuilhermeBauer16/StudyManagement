package com.github.guilhermebauer.studymanagement.service.contract;


import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LinkServiceContract {

    List<LinkEntity> create(List<LinkEntity> linkEntities);

    LinkVO update(com.github.guilhermebauer.studymanagement.model.values.LinkVO linkVO);

    LinkVO findLinkById(String id) ;

    @Transactional
    void delete(String id);

}

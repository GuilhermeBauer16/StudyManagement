package com.github.guilhermebauer.studymanagement.service.contract;

import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import org.springframework.transaction.annotation.Transactional;

public interface StudyMaterialServiceContract {


    StudyMaterialVO create(StudyMaterialVO studyMaterialVO) throws NoSuchFieldException, IllegalAccessException;

    StudyMaterialVO update(StudyMaterialVO studyMaterialVO) throws NoSuchFieldException, IllegalAccessException;

    StudyMaterialVO findByID(String id) throws NoSuchFieldException, IllegalAccessException;

    StudyMaterialVO findAll(StudyMaterialVO studyMaterialVO) throws NoSuchFieldException, IllegalAccessException;

    void delete(String id);

    StudyMaterialVO addLinkInStudyMaterial(LinkListToStudyMaterialRequest request) throws IllegalAccessException, NoSuchFieldException;

    StudyMaterialVO updateLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request) throws NoSuchFieldException, IllegalAccessException;

    @Transactional
    StudyMaterialVO deleteLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request) throws NoSuchFieldException, IllegalAccessException;

    StudyMaterialVO findAllLinkIntoStudyMaterial(StudyMaterialVO studyMaterialVO);

}

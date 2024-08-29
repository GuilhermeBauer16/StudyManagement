package com.github.guilhermebauer.studymanagement.service.contract;

import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.StudyMaterialUpdateRequest;
import com.github.guilhermebauer.studymanagement.response.StudyMaterialUpdateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface StudyMaterialServiceContract {


    StudyMaterialVO create(StudyMaterialVO studyMaterialVO) throws NoSuchFieldException, IllegalAccessException;

    StudyMaterialUpdateResponse update(StudyMaterialUpdateRequest request) throws NoSuchFieldException, IllegalAccessException;

    StudyMaterialVO findByID(String id) throws NoSuchFieldException, IllegalAccessException;

    Page<StudyMaterialVO> findAll(Pageable pageable) throws NoSuchFieldException, IllegalAccessException;

    void delete(String id);

    StudyMaterialVO addLinkInStudyMaterial(LinkListToStudyMaterialRequest request) throws IllegalAccessException, NoSuchFieldException;

    StudyMaterialVO updateLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request) throws NoSuchFieldException, IllegalAccessException;

    @Transactional
    StudyMaterialVO deleteLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request) throws NoSuchFieldException, IllegalAccessException;

    Page<LinkVO> findAllLinksInStudyMaterial(String studyMaterialId, Pageable pageable);

}

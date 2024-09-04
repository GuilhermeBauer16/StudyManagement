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


    StudyMaterialVO create(StudyMaterialVO studyMaterialVO);

    StudyMaterialUpdateResponse update(StudyMaterialUpdateRequest request);

    StudyMaterialVO findByID(String id);

    Page<StudyMaterialVO> findAll(Pageable pageable);

    void delete(String id);

    StudyMaterialVO addLinkInStudyMaterial(LinkListToStudyMaterialRequest request);

    StudyMaterialVO updateLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request);

    @Transactional
    StudyMaterialVO deleteLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request);

    Page<LinkVO> findAllLinksInStudyMaterial(String studyMaterialId, Pageable pageable);

}

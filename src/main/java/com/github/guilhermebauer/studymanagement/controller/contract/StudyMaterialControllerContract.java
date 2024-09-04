package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.StudyMaterialUpdateRequest;
import com.github.guilhermebauer.studymanagement.response.StudyMaterialUpdateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface StudyMaterialControllerContract {

    @PostMapping
    ResponseEntity<StudyMaterialVO> create(@RequestBody StudyMaterialVO studyMaterialVO) ;

    @PutMapping
    ResponseEntity<StudyMaterialUpdateResponse> update(@RequestBody StudyMaterialUpdateRequest request) ;

    @GetMapping(value = "/{id}")
    ResponseEntity<StudyMaterialVO> findById(@PathVariable(value = "id") String id);

    @GetMapping
    ResponseEntity<Page<StudyMaterialVO>> findAll(@PageableDefault(size = 5) Pageable pageable) ;

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable(value = "id") String id);

    @PostMapping(value = "/addLinks")
    ResponseEntity<StudyMaterialVO> addLinkInStudyMaterial(@RequestBody LinkListToStudyMaterialRequest request);

    @PutMapping(value = "/updateLink")
    ResponseEntity<StudyMaterialVO> updateLinkInStudyMaterial(@RequestBody SingleLinkToStudyMaterialRequest request);

    @GetMapping(value = "/findAllLinks/{id}")
    ResponseEntity<Page<LinkVO>> findAllLinksInStudyMaterial(@PathVariable(value = "id") String studyMaterialId, Pageable pageable);

    @DeleteMapping(value = "/deleteLinks")
    ResponseEntity<StudyMaterialVO> deleteLinkInStudyMaterial(@RequestBody SingleLinkToStudyMaterialRequest request);




}

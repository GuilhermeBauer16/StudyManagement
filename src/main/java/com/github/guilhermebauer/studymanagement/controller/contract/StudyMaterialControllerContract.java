package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface StudyMaterialControllerContract {

    @PostMapping
    ResponseEntity<StudyMaterialVO> create(@RequestBody StudyMaterialVO studyMaterialVO) throws IllegalAccessException, NoSuchFieldException;

    @PutMapping
    ResponseEntity<StudyMaterialVO> update(@RequestBody StudyMaterialVO studyMaterialVO) throws NoSuchFieldException, IllegalAccessException;

    @GetMapping(value = "/{id}")
    ResponseEntity<StudyMaterialVO> findById(@PathVariable(value = "id") String id) throws NoSuchFieldException, IllegalAccessException;

    @GetMapping
    ResponseEntity<StudyMaterialVO> findAll(StudyMaterialVO studyMaterialVO) throws NoSuchFieldException, IllegalAccessException;

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable(value = "id") String id);

    @PostMapping(value = "/addLinks")
    ResponseEntity<StudyMaterialVO> addLinkInStudyMaterial(@RequestBody LinkListToStudyMaterialRequest request) throws IllegalAccessException, NoSuchFieldException;

    @PutMapping(value = "/updateLink")
    ResponseEntity<StudyMaterialVO> updateLinkInStudyMaterial(@RequestBody SingleLinkToStudyMaterialRequest request) throws NoSuchFieldException, IllegalAccessException;

    @GetMapping(value = "/findAllLinks/{id}")
    ResponseEntity<Page<LinkVO>> findAllLinksInStudyMaterial(@PathVariable(value = "id") String studyMaterialId, Pageable pageable);

    @DeleteMapping(value = "/deleteLinks")
    ResponseEntity<StudyMaterialVO> deleteLinkInStudyMaterial(@RequestBody SingleLinkToStudyMaterialRequest request) throws NoSuchFieldException, IllegalAccessException;




}

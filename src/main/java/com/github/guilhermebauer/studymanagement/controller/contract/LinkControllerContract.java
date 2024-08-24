package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LinkControllerContract {


    List<LinkEntity> create(List<LinkEntity> linkEntities) throws IllegalAccessException;

    @PutMapping
    ResponseEntity<com.github.guilhermebauer.studymanagement.model.values.LinkVO> update(@RequestBody com.github.guilhermebauer.studymanagement.model.values.LinkVO linkVO) throws NoSuchFieldException, IllegalAccessException;

    @GetMapping(value = "/{id}")
    ResponseEntity<com.github.guilhermebauer.studymanagement.model.values.LinkVO>  findLinkById(@PathVariable(value = "id") String id) throws NoSuchFieldException, IllegalAccessException;

    @GetMapping
    ResponseEntity<Page<com.github.guilhermebauer.studymanagement.model.values.LinkVO>> findAllLinks(Pageable pageable);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable(value = "id")String id);
}

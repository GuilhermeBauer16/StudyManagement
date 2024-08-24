package com.github.guilhermebauer.studymanagement.controller;

import com.github.guilhermebauer.studymanagement.controller.contract.LinkControllerContract;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController implements LinkControllerContract {

    private final LinkService service;

    @Autowired
    public LinkController(LinkService service) {
        this.service = service;
    }

    @Override
    public List<LinkEntity> create(List<LinkEntity> linkEntities) throws IllegalAccessException {
        return List.of();
    }

    @Override
    public ResponseEntity<com.github.guilhermebauer.studymanagement.model.values.LinkVO> update(com.github.guilhermebauer.studymanagement.model.values.LinkVO linkVO) throws NoSuchFieldException, IllegalAccessException {
        com.github.guilhermebauer.studymanagement.model.values.LinkVO update = service.update(linkVO);
        return ResponseEntity.ok(update);
    }

    @Override
    public ResponseEntity<com.github.guilhermebauer.studymanagement.model.values.LinkVO> findLinkById(String id) throws NoSuchFieldException, IllegalAccessException {

        com.github.guilhermebauer.studymanagement.model.values.LinkVO linkById = service.findLinkById(id);
        return ResponseEntity.ok(linkById);
    }

    @Override
    public ResponseEntity<Page<com.github.guilhermebauer.studymanagement.model.values.LinkVO>> findAllLinks(Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

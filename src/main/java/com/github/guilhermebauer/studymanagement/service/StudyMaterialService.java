package com.github.guilhermebauer.studymanagement.service;


import com.github.guilhermebauer.studymanagement.factory.StudyMaterialFactory;

import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import com.github.guilhermebauer.studymanagement.repository.LinkRepository;
import com.github.guilhermebauer.studymanagement.repository.StudyMaterialRepository;
import com.github.guilhermebauer.studymanagement.service.contract.StudyMaterialServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class StudyMaterialService implements StudyMaterialServiceContract {

    @Autowired
    private StudyMaterialRepository studyMaterialRepository;

    @Autowired
    private LinkService linkService;
    @Override
    @Transactional
    public StudyMaterialEntity create(StudyMaterialEntity studyMaterialEntity) {

        // Handle possible nulls and empty lists
        List<LinkEntity> linkEntityList = linkService.create(studyMaterialEntity.getLinks());

        StudyMaterialEntity studyMaterialEntity1 = StudyMaterialFactory.create(
                studyMaterialEntity.getTitle(),
                studyMaterialEntity.getContent(),
                studyMaterialEntity.getCourseEntity(),
                linkEntityList

        );

        return studyMaterialRepository.save(studyMaterialEntity1);
    }
}
package com.github.guilhermebauer.studymanagement.service;


import com.github.guilhermebauer.studymanagement.exception.FieldNotFound;
import com.github.guilhermebauer.studymanagement.exception.StudyMaterialNotFoundException;
import com.github.guilhermebauer.studymanagement.factory.StudyMaterialFactory;
import com.github.guilhermebauer.studymanagement.mapper.BuildMapper;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.repository.LinkRepository;
import com.github.guilhermebauer.studymanagement.repository.StudyMaterialRepository;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.service.contract.StudyMaterialServiceContract;
import com.github.guilhermebauer.studymanagement.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;


@Service
public class StudyMaterialService implements StudyMaterialServiceContract {

    private static final String STUDY_MATERIAL_NOT_FOUND = "The study material was not found";
    private final StudyMaterialRepository repository;


    private final LinkService linkService;
    private final LinkRepository linkRepository;

    @Autowired
    public StudyMaterialService(StudyMaterialRepository studyMaterialRepository, LinkService linkService,
                                LinkRepository linkRepository) {
        this.repository = studyMaterialRepository;
        this.linkService = linkService;
        this.linkRepository = linkRepository;
    }


    @Override
    @Transactional
    public StudyMaterialVO create(StudyMaterialVO studyMaterialVO) throws NoSuchFieldException, IllegalAccessException {

        ValidatorUtils.checkObjectIsNullOrThrowException(studyMaterialVO, STUDY_MATERIAL_NOT_FOUND, StudyMaterialNotFoundException.class);
        List<LinkEntity> linkEntityList = linkService.create(studyMaterialVO.getLinks());
        StudyMaterialEntity studyMaterialEntityToSave = StudyMaterialFactory.create(
                studyMaterialVO.getTitle(),
                studyMaterialVO.getContent(),
                studyMaterialVO.getCourseEntity(),
                linkEntityList
        );
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(studyMaterialEntityToSave,STUDY_MATERIAL_NOT_FOUND, FieldNotFound.class);
        StudyMaterialEntity savedStudyMaterial = repository.save(studyMaterialEntityToSave);
        return BuildMapper.parseObject(new StudyMaterialVO(), savedStudyMaterial);

    }

    @Override
    public StudyMaterialVO update(StudyMaterialVO studyMaterialVO) throws NoSuchFieldException, IllegalAccessException {
        return null;
    }

    @Override
    public StudyMaterialVO findByID(String id) throws NoSuchFieldException, IllegalAccessException {

        StudyMaterialEntity studyMaterialEntity = repository.findById(id).orElseThrow(() -> new StudyMaterialNotFoundException(STUDY_MATERIAL_NOT_FOUND));
        return BuildMapper.parseObject(new StudyMaterialVO(), studyMaterialEntity);
    }

    @Override
    public StudyMaterialVO findAll(StudyMaterialVO studyMaterialVO) throws NoSuchFieldException, IllegalAccessException {
        return null;
    }

    @Override
    public void delete(String id) {

        StudyMaterialEntity studyMaterialEntity = repository.findById(id)
                .orElseThrow(() -> new StudyMaterialNotFoundException(STUDY_MATERIAL_NOT_FOUND));

        repository.delete(studyMaterialEntity);


    }

    @Override
    public StudyMaterialVO addLinkInStudyMaterial(LinkListToStudyMaterialRequest request) throws IllegalAccessException, NoSuchFieldException {

        StudyMaterialEntity studyMaterialEntity = repository.findById(request.getId())
                .orElseThrow(() -> new StudyMaterialNotFoundException(STUDY_MATERIAL_NOT_FOUND));

        List<LinkEntity> links = studyMaterialEntity.getLinks();
        List<LinkEntity> linkEntityList = linkService.create(request.getLinks());
        links.addAll(linkEntityList);
        studyMaterialEntity.setLinks(links);
        StudyMaterialEntity addedLinksToAStudyMaterial = repository.save(studyMaterialEntity);

        return BuildMapper.parseObject(new StudyMaterialVO(), addedLinksToAStudyMaterial);
    }


    @Override
    @Transactional
    public StudyMaterialVO updateLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request) throws NoSuchFieldException, IllegalAccessException {

        StudyMaterialEntity studyMaterialEntity = repository.findById(request.getId())
                .orElseThrow(() -> new StudyMaterialNotFoundException(STUDY_MATERIAL_NOT_FOUND));

        List<LinkEntity> links = studyMaterialEntity.getLinks();
        for (LinkEntity linkEntity : links) {

            if (linkEntity.getId().equals(request.getLink().getId())) {
                LinkVO linkVO1 = BuildMapper.parseObject(new LinkVO(), linkEntity);
                linkService.update(linkVO1);
                // Perform any other updates necessary
                break;
            }
        }
        StudyMaterialEntity addedLinksToAStudyMaterial = repository.save(studyMaterialEntity);

        return BuildMapper.parseObject(new StudyMaterialVO(), addedLinksToAStudyMaterial);
    }

    @Override
    @Transactional
    public StudyMaterialVO deleteLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request) throws NoSuchFieldException, IllegalAccessException {

        StudyMaterialEntity studyMaterialEntity = repository.findById(request.getId())
                .orElseThrow(() -> new StudyMaterialNotFoundException(STUDY_MATERIAL_NOT_FOUND));

        Iterator<LinkEntity> linkIterator = studyMaterialEntity.getLinks().iterator();

        while (linkIterator.hasNext()) {
            LinkEntity linkEntity = linkIterator.next();
            if (linkEntity.getId().equals(request.getLink().getId())) {
                linkIterator.remove();
                linkService.delete(linkEntity.getId());
                break;
            }
        }

        repository.save(studyMaterialEntity);

        return BuildMapper.parseObject(new StudyMaterialVO(), studyMaterialEntity);
    }

    @Override
    public StudyMaterialVO findAllLinkIntoStudyMaterial(StudyMaterialVO studyMaterialVO) {
        return null;
    }
}
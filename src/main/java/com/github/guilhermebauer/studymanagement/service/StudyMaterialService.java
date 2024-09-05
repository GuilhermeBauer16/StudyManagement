package com.github.guilhermebauer.studymanagement.service;


import com.github.guilhermebauer.studymanagement.exception.FieldNotFound;
import com.github.guilhermebauer.studymanagement.exception.StudyMaterialNotFoundException;
import com.github.guilhermebauer.studymanagement.factory.StudyMaterialFactory;
import com.github.guilhermebauer.studymanagement.mapper.BuildMapper;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.repository.StudyMaterialRepository;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.StudyMaterialUpdateRequest;
import com.github.guilhermebauer.studymanagement.response.StudyMaterialUpdateResponse;
import com.github.guilhermebauer.studymanagement.service.contract.StudyMaterialServiceContract;
import com.github.guilhermebauer.studymanagement.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class StudyMaterialService implements StudyMaterialServiceContract {

    private static final String STUDY_MATERIAL_NOT_FOUND = "The study material was not found";

    private final StudyMaterialRepository repository;

    private final LinkService linkService;


    @Autowired
    public StudyMaterialService(StudyMaterialRepository studyMaterialRepository, LinkService linkService) {
        this.repository = studyMaterialRepository;
        this.linkService = linkService;
    }


    @Override
    @Transactional
    public StudyMaterialVO create(StudyMaterialVO studyMaterialVO) {

        ValidatorUtils.checkObjectIsNullOrThrowException(studyMaterialVO, STUDY_MATERIAL_NOT_FOUND, StudyMaterialNotFoundException.class);
        List<LinkEntity> linkEntityList = linkService.create(studyMaterialVO.getLinks());
        StudyMaterialEntity studyMaterialEntityToSave = StudyMaterialFactory.create(
                studyMaterialVO.getTitle(),
                studyMaterialVO.getContent(),
                studyMaterialVO.getCourseEntity(),
                linkEntityList
        );
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(studyMaterialEntityToSave, STUDY_MATERIAL_NOT_FOUND, FieldNotFound.class);
        StudyMaterialEntity savedStudyMaterial = repository.save(studyMaterialEntityToSave);
        return BuildMapper.parseObject(new StudyMaterialVO(), savedStudyMaterial);

    }

    @Override
    public StudyMaterialUpdateResponse update(StudyMaterialUpdateRequest request){

        ValidatorUtils.checkObjectIsNullOrThrowException(request,STUDY_MATERIAL_NOT_FOUND,StudyMaterialNotFoundException.class);

        StudyMaterialEntity studyMaterialEntity = repository.findById(request.getId())
                .orElseThrow(() -> new StudyMaterialNotFoundException(STUDY_MATERIAL_NOT_FOUND));

        StudyMaterialEntity updatedStudyMaterialEntity = ValidatorUtils.updateFieldIfNotNull
                (studyMaterialEntity, request, STUDY_MATERIAL_NOT_FOUND, StudyMaterialNotFoundException.class);

        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(updatedStudyMaterialEntity,STUDY_MATERIAL_NOT_FOUND,FieldNotFound.class);

        StudyMaterialEntity savedStudyMaterialEntity = repository.save(updatedStudyMaterialEntity);
        return BuildMapper.parseObject(new StudyMaterialUpdateResponse(), savedStudyMaterialEntity);
    }

    @Override
    public StudyMaterialVO findByID(String id) {

        StudyMaterialEntity studyMaterialEntity = repository.findById(id).orElseThrow(() -> new StudyMaterialNotFoundException(STUDY_MATERIAL_NOT_FOUND));
        return BuildMapper.parseObject(new StudyMaterialVO(), studyMaterialEntity);
    }

    @Override
    public Page<StudyMaterialVO> findAll(Pageable pageable) {

        Page<StudyMaterialEntity> allLinks = repository.findAll(pageable);
        List<StudyMaterialEntity> content = allLinks.getContent();
        List<StudyMaterialVO> informationResponses = new ArrayList<>();
        for(StudyMaterialEntity studyMaterialEntity : content){

            informationResponses.add(BuildMapper.parseObject(new StudyMaterialVO(), studyMaterialEntity));
        }

        return new PageImpl<>(informationResponses, pageable, allLinks.getTotalElements());
    }

    @Override
    public void delete(String id) {

        StudyMaterialEntity studyMaterialEntity = repository.findById(id)
                .orElseThrow(() -> new StudyMaterialNotFoundException(STUDY_MATERIAL_NOT_FOUND));

        repository.delete(studyMaterialEntity);


    }

    @Override
    public StudyMaterialVO addLinkInStudyMaterial(LinkListToStudyMaterialRequest request){

        StudyMaterialEntity studyMaterialEntity = repository.findById(request.getId())
                .orElseThrow(() -> new StudyMaterialNotFoundException(STUDY_MATERIAL_NOT_FOUND));

        List<LinkEntity> links = new ArrayList<>(studyMaterialEntity.getLinks());
        List<LinkEntity> linkEntityList = linkService.create(request.getLinks());
        links.addAll(linkEntityList);
        studyMaterialEntity.setLinks(links);
        StudyMaterialEntity addedLinksToAStudyMaterial = repository.save(studyMaterialEntity);

        return BuildMapper.parseObject(new StudyMaterialVO(), addedLinksToAStudyMaterial);
    }


    @Override
    @Transactional
    public StudyMaterialVO updateLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request) {

        StudyMaterialEntity studyMaterialEntity = repository.findById(request.getId())
                .orElseThrow(() -> new StudyMaterialNotFoundException(STUDY_MATERIAL_NOT_FOUND));

        List<LinkEntity> links = studyMaterialEntity.getLinks();
        for (LinkEntity linkEntity : links) {

            if (linkEntity.getId().equals(request.getLink().getId())) {
                LinkVO linkVO1 = BuildMapper.parseObject(new LinkVO(), linkEntity);
                linkService.update(linkVO1);
                break;
            }
        }
        StudyMaterialEntity addedLinksToAStudyMaterial = repository.save(studyMaterialEntity);

        return BuildMapper.parseObject(new StudyMaterialVO(), addedLinksToAStudyMaterial);
    }

    @Override
    @Transactional
    public void deleteLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request)  {

        StudyMaterialEntity studyMaterialEntity = repository.findById(request.getId())
                .orElseThrow(() -> new StudyMaterialNotFoundException(STUDY_MATERIAL_NOT_FOUND));

        List<LinkEntity> mutableLinks = new ArrayList<>(studyMaterialEntity.getLinks());
        Iterator<LinkEntity> linkIterator = mutableLinks.iterator();

        while (linkIterator.hasNext()) {
            LinkEntity linkEntity = linkIterator.next();
            if (linkEntity.getId().equals(request.getLink().getId())) {
                linkIterator.remove();
                linkService.delete(linkEntity.getId());
                break;
            }
        }

        studyMaterialEntity.setLinks(mutableLinks);
        repository.save(studyMaterialEntity);

    }

    public Page<LinkVO> findAllLinksInStudyMaterial(String studyMaterialId, Pageable pageable) {
        Page<LinkEntity> linkEntities = repository.findLinksByStudyMaterialId(studyMaterialId, pageable);

        return linkEntities.map(linkEntity -> BuildMapper.parseObject(new LinkVO(), linkEntity));
    }
}
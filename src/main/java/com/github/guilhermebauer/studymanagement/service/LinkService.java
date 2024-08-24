package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.exception.FieldNotFound;
import com.github.guilhermebauer.studymanagement.exception.LinkNotFoundException;
import com.github.guilhermebauer.studymanagement.factory.LinkEntityFactory;
import com.github.guilhermebauer.studymanagement.mapper.BuildMapper;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.repository.LinkRepository;
import com.github.guilhermebauer.studymanagement.service.contract.LinkServiceContract;
import com.github.guilhermebauer.studymanagement.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkService implements LinkServiceContract {

    private static final String LINK_NOT_FOUND = "The link was not found!";

    private final LinkRepository repository;
    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.repository = linkRepository;
    }



    @Override
    public List<LinkEntity> create(List<LinkEntity> linkEntities) throws IllegalAccessException {

        List<LinkEntity> savedLinkEntities = new ArrayList<>();

        for(LinkEntity linkEntity : linkEntities){
            LinkEntity linkEntityCreated = LinkEntityFactory.create(linkEntity.getUrl(), linkEntity.getDescription());
            ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(linkEntityCreated,LINK_NOT_FOUND, FieldNotFound.class);
            LinkEntity save = repository.save(linkEntityCreated);
            savedLinkEntities.add(save);

        }
        return savedLinkEntities ;
    }

    @Override
    @Transactional
    public LinkVO update(LinkVO linkVO) throws NoSuchFieldException, IllegalAccessException {

        LinkEntity linkEntity = repository.findById(linkVO.getId()).orElseThrow(() -> new LinkNotFoundException(LINK_NOT_FOUND));
        LinkEntity updatedLinkEntity = ValidatorUtils.updateFieldIfNotNull(linkEntity, linkVO, LINK_NOT_FOUND, LinkNotFoundException.class);
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(linkEntity,LINK_NOT_FOUND, FieldNotFound.class);
        LinkEntity savedLinkEntity = repository.save(updatedLinkEntity);

        return BuildMapper.parseObject(new LinkVO(), savedLinkEntity);
    }

    @Override
    public LinkVO findLinkById(String id) throws NoSuchFieldException, IllegalAccessException {

        LinkEntity linkEntity = repository.findById(id).orElseThrow(() -> new LinkNotFoundException(LINK_NOT_FOUND));
        return BuildMapper.parseObject(new LinkVO(), linkEntity);
    }

    @Override
    public Page<LinkVO> findAllLinks(Pageable pageable) {
        return null;
    }

    @Override
    public void delete(String id) {
        LinkEntity linkEntity = repository.findById(id).orElseThrow(() -> new LinkNotFoundException(LINK_NOT_FOUND));
        repository.delete(linkEntity);
    }
}

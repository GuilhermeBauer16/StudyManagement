package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.factory.LinkEntityFactory;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.repository.LinkRepository;
import com.github.guilhermebauer.studymanagement.service.contract.LinkServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkService implements LinkServiceContract {

    private final LinkRepository linkRepository;
    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }


    @Override
    public List<LinkEntity> create(List<LinkEntity> linkEntity) {
        List<LinkEntity> linkEntityList = new ArrayList<>();

        for (LinkEntity link : linkEntity ) {
            LinkEntity linkEntityCreated = LinkEntityFactory.create(link.getUrl(), link.getDescription());
            LinkEntity savedLinkEntity = linkRepository.save(linkEntityCreated);
            linkEntityList.add(savedLinkEntity);
        }
        return linkEntityList;

    }
}

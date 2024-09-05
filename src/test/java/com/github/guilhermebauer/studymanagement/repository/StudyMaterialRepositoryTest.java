package com.github.guilhermebauer.studymanagement.repository;

import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import com.github.guilhermebauer.studymanagement.testContainers.AbstractionIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudyMaterialRepositoryTest extends AbstractionIntegrationTest {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private StudyMaterialRepository studyMaterialRepository;

    @Autowired
    private CourseRepository courseRepository;

    private StudyMaterialEntity studyMaterialEntity;

    private static final String ID = "5f68880e-7356-4c86-a4a9-f8cc16e2ec87";
    private static final String URL = "https://start.spring.io/";
    private static final String DESCRIPTION = "Spring Boot Tutorial";
    private static final String TITLE = "Introduction to Spring Boot";
    private static final String CONTENT = "This is a comprehensive guide to Spring Boot.";
    private static final String COURSE_TITLE = "Math";
    private static final String COURSE_DESCRIPTION = "Math is a discipline that work with numbers";


    @BeforeEach
    public void setup() {

        LinkEntity linkEntity = new LinkEntity(ID, URL, DESCRIPTION);
        linkRepository.save(linkEntity);

        CourseEntity courseEntity = new CourseEntity(ID, COURSE_TITLE, COURSE_DESCRIPTION);
        courseRepository.save(courseEntity);


        studyMaterialEntity = new StudyMaterialEntity(ID, TITLE, CONTENT, courseEntity, List.of(linkEntity));

        studyMaterialEntity = studyMaterialRepository.save(studyMaterialEntity);
    }

    @Test
    void testFindLinksByStudyMaterialId_WhenTheLinksWasFound_ShouldReturnALinkPageableList() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<LinkEntity> linksByStudyMaterialId = studyMaterialRepository.findLinksByStudyMaterialId(studyMaterialEntity.getId(), pageable);

        List<LinkEntity> content = linksByStudyMaterialId.getContent();
        LinkEntity linkEntity = content.getFirst();

        Assertions.assertEquals(DESCRIPTION, linkEntity.getDescription());
        Assertions.assertEquals(URL, linkEntity.getUrl());


    }
}

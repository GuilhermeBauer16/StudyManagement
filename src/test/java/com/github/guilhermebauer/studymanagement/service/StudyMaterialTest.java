package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.exception.StudyMaterialNotFoundException;
import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.repository.StudyMaterialRepository;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.StudyMaterialUpdateRequest;
import com.github.guilhermebauer.studymanagement.response.StudyMaterialUpdateResponse;
import com.github.guilhermebauer.studymanagement.utils.ValidatorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyMaterialTest {

    @Mock
    private StudyMaterialRepository repository;

    @Mock
    private StudyMaterialVO studyMaterialVO;

    @Mock
    private StudyMaterialEntity studyMaterialEntity;

    @Mock
    private LinkEntity linkEntity;

    @Mock
    private LinkVO linkVO;

    @Mock
    private LinkListToStudyMaterialRequest linkListToStudyMaterialRequest;

    @Mock
    private SingleLinkToStudyMaterialRequest singleLinkToStudyMaterialRequest;

    @Mock
    private StudyMaterialUpdateRequest studyMaterialUpdateRequest;

    @Mock
    private CourseEntity courseEntity;

    @InjectMocks
    private StudyMaterialService service;

    @Mock
    private LinkService linkService;

    private static final String STUDY_MATERIAL_NOT_FOUND = "The study material was not found";


    private static final String INVALID_ID = "5f68880";
    private static final String ID = "5f68880e-7356-4c86-a4a9-f8cc16e2ec87";
    private static final String URL = "https://start.spring.io/";
    private static final String DESCRIPTION = "Spring Boot Tutorial";
    private static final String TITLE = "Introduction to Spring Boot";
    private static final String CONTENT = "This is a comprehensive guide to Spring Boot.";
    private final String COURSE_TITLE = "Math";
    private final String COURSE_DESCRIPTION = "Math is a discipline that work with numbers";


    @BeforeEach
    void setUp() {

        linkVO = new LinkVO(ID, URL, DESCRIPTION);
        linkEntity = new LinkEntity(ID, URL, DESCRIPTION);
        courseEntity = new CourseEntity(ID, COURSE_TITLE, COURSE_DESCRIPTION);
        studyMaterialVO = new StudyMaterialVO(ID, TITLE, CONTENT, courseEntity, List.of(linkEntity));
        studyMaterialEntity = new StudyMaterialEntity(ID, TITLE, CONTENT, courseEntity, List.of(linkEntity));
        singleLinkToStudyMaterialRequest = new SingleLinkToStudyMaterialRequest(ID, linkVO);
        linkListToStudyMaterialRequest = new LinkListToStudyMaterialRequest(ID, List.of(linkEntity));
        studyMaterialUpdateRequest = new StudyMaterialUpdateRequest(ID, TITLE, CONTENT);

    }

    @Test
    void testCreateStudyMaterial_WhenCreateStudyMaterial_ShouldReturnStudyMaterialObject()  {

        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = Mockito.mockStatic(ValidatorUtils.class)) {

            List<LinkEntity> linkEntities = List.of(linkEntity);
            mockedValidatorUtils.when(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any(Class.class)))
                    .thenAnswer(invocation -> null);

            mockedValidatorUtils.when(() -> ValidatorUtils.updateFieldIfNotNull(any(StudyMaterialEntity.class),
                            any(StudyMaterialUpdateRequest.class), anyString(), any()))
                    .thenAnswer(invocation -> studyMaterialEntity);

            mockedValidatorUtils.when(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()))
                    .thenAnswer(invocation -> null);

            when(linkService.create(linkEntities)).thenReturn(linkEntities);
            when(repository.save(any(StudyMaterialEntity.class))).thenReturn(studyMaterialEntity);

            StudyMaterialVO createdStudyMaterial = service.create(studyMaterialVO);

            verify(repository, times(1)).save(any(StudyMaterialEntity.class));
            verify(linkService, times(1)).create(anyList());

            mockedValidatorUtils.verify(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any(Class.class)));
            mockedValidatorUtils.verify(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()));

            assertNotNull(createdStudyMaterial);
            assertNotNull(createdStudyMaterial.getId());
            assertEquals(CONTENT, createdStudyMaterial.getContent());
            assertEquals(TITLE, createdStudyMaterial.getTitle());
            assertEquals(COURSE_TITLE, createdStudyMaterial.getCourseEntity().getTitle());
            assertEquals(COURSE_DESCRIPTION, createdStudyMaterial.getCourseEntity().getDescription());

        }
    }

    @Test
    void testCreateStudyMaterial_WhenStudyMaterialIsNull_ShouldThrowStudyMaterialNotFoundException() {

        StudyMaterialNotFoundException exception = assertThrows(StudyMaterialNotFoundException.class, () ->
                service.create(null));

        assertNotNull(exception);
        assertEquals(StudyMaterialNotFoundException.ERROR.formatErrorMessage(STUDY_MATERIAL_NOT_FOUND)
                , exception.getMessage());


    }


    @Test
    void testUpdateStudyMaterial_WhenUpdatedStudyMaterial_ShouldReturnStudyMaterialObject()  {
        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = Mockito.mockStatic(ValidatorUtils.class)) {

            studyMaterialEntity.setContent("A Spring Boot guide");
            studyMaterialEntity.setTitle("Spring Boot introduction");

            mockedValidatorUtils.when(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any(Class.class)))
                    .thenAnswer(invocation -> null);

            mockedValidatorUtils.when(() -> ValidatorUtils.updateFieldIfNotNull(any(StudyMaterialEntity.class),
                            any(StudyMaterialUpdateRequest.class), anyString(), any()))
                    .thenAnswer(invocation -> studyMaterialEntity);

            mockedValidatorUtils.when(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()))
                    .thenAnswer(invocation -> null);

            when(repository.findById(anyString())).thenReturn(Optional.of(studyMaterialEntity));
            when(repository.save(any(StudyMaterialEntity.class))).thenReturn(studyMaterialEntity);

            StudyMaterialUpdateResponse update = service.update(studyMaterialUpdateRequest);

            verify(repository, times(1)).save(any());
            verify(repository, times(1)).findById(anyString());

            mockedValidatorUtils.verify(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any(Class.class)));
            mockedValidatorUtils.verify(() -> ValidatorUtils.updateFieldIfNotNull(any(), any(), anyString(), any()));
            mockedValidatorUtils.verify(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()));

            assertNotNull(update);
            assertNotNull(update.getId());
            assertEquals("A Spring Boot guide", update.getContent());
            assertEquals("Spring Boot introduction", update.getTitle());

        }
    }

    @Test
    void testUpdateStudyMaterial_WhenStudyMaterialIdIsNotInsertIntoDatabase_ShouldThrowStudyMaterialNotFoundException() {

        StudyMaterialNotFoundException exception = assertThrows(StudyMaterialNotFoundException.class, () ->
                service.update(studyMaterialUpdateRequest));

        assertNotNull(exception);
        assertEquals(StudyMaterialNotFoundException.ERROR.formatErrorMessage(STUDY_MATERIAL_NOT_FOUND)
                , exception.getMessage());


    }

    @Test
    void testFindStudyMaterial_WhenFindStudyMaterialById_ShouldReturnStudyMaterialObject()  {


        when(repository.findById(anyString())).thenReturn(Optional.of(studyMaterialEntity));

        StudyMaterialVO studyMaterialByID = service.findByID(ID);

        verify(repository, times(1)).findById(anyString());

        assertNotNull(studyMaterialByID);
        assertNotNull(studyMaterialByID.getId());
        assertEquals(CONTENT, studyMaterialByID.getContent());
        assertEquals(TITLE, studyMaterialByID.getTitle());
        assertEquals(COURSE_TITLE, studyMaterialByID.getCourseEntity().getTitle());
        assertEquals(COURSE_DESCRIPTION, studyMaterialByID.getCourseEntity().getDescription());


    }

    @Test
    void testFindStudyMaterialById_WhenStudyMaterialIsNotInsertIntoDatabase_ShouldThrowStudyMaterialNotFoundException() {

        StudyMaterialNotFoundException exception = assertThrows(StudyMaterialNotFoundException.class, () ->
                service.findByID(ID));

        assertNotNull(exception);
        assertEquals(StudyMaterialNotFoundException.ERROR.formatErrorMessage(STUDY_MATERIAL_NOT_FOUND)
                , exception.getMessage());


    }

    @Test
    void testStudyMaterial_When_FindAll_ShouldReturnStudyMaterialList()  {

        List<StudyMaterialEntity> studyMaterialEntities = List.of(studyMaterialEntity);

        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(studyMaterialEntities));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<StudyMaterialVO> foundedListOfStudyMaterialVO = service.findAll(pageRequest);

        verify(repository, times(1)).findAll(any(Pageable.class));

        StudyMaterialVO foundedStudyMaterialVO = foundedListOfStudyMaterialVO.getContent().getFirst();

        assertEquals(1, foundedListOfStudyMaterialVO.getTotalElements());

        assertNotNull(foundedStudyMaterialVO);
        assertNotNull(foundedStudyMaterialVO.getCourseEntity());

        assertEquals(CONTENT, foundedStudyMaterialVO.getContent());
        assertEquals(TITLE, foundedStudyMaterialVO.getTitle());
        assertEquals(COURSE_TITLE, foundedStudyMaterialVO.getCourseEntity().getTitle());
        assertEquals(COURSE_DESCRIPTION, foundedStudyMaterialVO.getCourseEntity().getDescription());

    }

    @Test
    void testDelete_WhenDeletingStudyMaterial_thenDoNothing() {

        when(repository.findById(ID)).thenReturn(Optional.of(studyMaterialEntity));
        doNothing().when(repository).delete(studyMaterialEntity);
        service.delete(ID);
        verify(repository, times(1)).delete(any(StudyMaterialEntity.class));
    }


    @Test
    void testDelete_WhenStudyMaterialIdNotFoundIntoDatabase_ShouldThrowStudyMaterialNotFoundException() {

        StudyMaterialNotFoundException exception = assertThrows(StudyMaterialNotFoundException.class, () ->
                service.delete(ID));

        assertNotNull(exception);
        assertEquals(StudyMaterialNotFoundException.ERROR.formatErrorMessage(STUDY_MATERIAL_NOT_FOUND)
                , exception.getMessage());
    }

    @Test
    void testAddLinkInStudyMaterial_WhenCreateStudyMaterial_ShouldReturnStudyMaterialObject()  {

        List<LinkEntity> linkEntities = List.of(linkEntity);

        when(repository.findById(anyString())).thenReturn(Optional.of(studyMaterialEntity));
        when(linkService.create(linkEntities)).thenReturn(linkEntities);
        when(repository.save(any(StudyMaterialEntity.class))).thenReturn(studyMaterialEntity);

        StudyMaterialVO addLinksToStudyMaterial = service.addLinkInStudyMaterial(linkListToStudyMaterialRequest);

        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findById(anyString());


        assertNotNull(addLinksToStudyMaterial);
        assertNotNull(addLinksToStudyMaterial.getId());
        assertEquals(URL, addLinksToStudyMaterial.getLinks().getFirst().getUrl());
        assertEquals(DESCRIPTION, addLinksToStudyMaterial.getLinks().getFirst().getDescription());


    }

    @Test
    void testAddLinkInStudyMaterial_WhenStudyMaterialIdNotFoundIntoDatabase_ShouldThrowStudyMaterialNotFoundException() {

        StudyMaterialNotFoundException exception = assertThrows(StudyMaterialNotFoundException.class, () ->
                service.addLinkInStudyMaterial(linkListToStudyMaterialRequest));

        assertNotNull(exception);
        assertEquals(StudyMaterialNotFoundException.ERROR.formatErrorMessage(STUDY_MATERIAL_NOT_FOUND)
                , exception.getMessage());
    }

    @Test
    void testUpdateLinkInStudyMaterial_WhenUpdatedStudyMaterial_ShouldReturnStudyMaterialObject()  {


        when(repository.findById(anyString())).thenReturn(Optional.of(studyMaterialEntity));
        when(repository.save(any(StudyMaterialEntity.class))).thenReturn(studyMaterialEntity);

        StudyMaterialVO updateLinksToStudyMaterial = service.updateLinkInStudyMaterial(singleLinkToStudyMaterialRequest);

        verify(repository, times(1)).save(any());
        verify(repository, times(1)).findById(anyString());


        assertNotNull(updateLinksToStudyMaterial);
        assertNotNull(updateLinksToStudyMaterial.getId());
        assertEquals(URL, updateLinksToStudyMaterial.getLinks().getFirst().getUrl());
        assertEquals(DESCRIPTION, updateLinksToStudyMaterial.getLinks().getFirst().getDescription());


    }

    @Test
    void testUpdateLinkInStudyMaterial_WhenStudyMaterialIdNotFoundIntoDatabase_ShouldThrowStudyMaterialNotFoundException() {

        StudyMaterialNotFoundException exception = assertThrows(StudyMaterialNotFoundException.class, () ->
                service.updateLinkInStudyMaterial(singleLinkToStudyMaterialRequest));

        assertNotNull(exception);
        assertEquals(StudyMaterialNotFoundException.ERROR.formatErrorMessage(STUDY_MATERIAL_NOT_FOUND)
                , exception.getMessage());
    }

    @Test
    void testDeleteLinkInStudyMaterial_WhenDeletingStudyMaterialLink_thenDoNothing() {

        when(repository.findById(ID)).thenReturn(Optional.of(studyMaterialEntity));
        when(repository.save(any(StudyMaterialEntity.class))).thenReturn(studyMaterialEntity);

        service.deleteLinkInStudyMaterial(singleLinkToStudyMaterialRequest);

        verify(repository, times(1)).findById(ID);
        verify(repository, times(1)).save(any(StudyMaterialEntity.class));
        verify(linkService, times(1)).delete(singleLinkToStudyMaterialRequest.getLink().getId());
    }

    @Test
    void testUpdateLinkInStudyMaterial_When_LinkNotFound() {

        when(repository.findById(anyString())).thenReturn(Optional.of(studyMaterialEntity));
        when(repository.save(any(StudyMaterialEntity.class))).thenReturn(studyMaterialEntity);
        singleLinkToStudyMaterialRequest.getLink().setId(INVALID_ID);
        service.updateLinkInStudyMaterial(singleLinkToStudyMaterialRequest);

        verify(repository,times(1)).findById(anyString());
        verify(repository,times(1)).save(any());


    }

    @Test
    void testDeleteLinkInStudyMaterial_WhenStudyMaterialIdNotFoundIntoDatabase_ShouldThrowStudyMaterialNotFoundException() {

        StudyMaterialNotFoundException exception = assertThrows(StudyMaterialNotFoundException.class, () ->
                service.deleteLinkInStudyMaterial(singleLinkToStudyMaterialRequest));

        assertNotNull(exception);
        assertEquals(StudyMaterialNotFoundException.ERROR.formatErrorMessage(STUDY_MATERIAL_NOT_FOUND)
                , exception.getMessage());
    }

    @Test
    void testDeleteLinkInStudyMaterial_When_LinkNotFound() {

        when(repository.findById(INVALID_ID)).thenReturn(Optional.of(studyMaterialEntity));
        singleLinkToStudyMaterialRequest.getLink().setId(INVALID_ID);
        singleLinkToStudyMaterialRequest.setId(INVALID_ID);
        service.deleteLinkInStudyMaterial(singleLinkToStudyMaterialRequest);
        assertEquals(1, studyMaterialEntity.getLinks().size());
    }

    @Test
    void testFindAlLLinkInStudyMaterial_WhenFinalAlLStudyMaterial_ShouldReturnStudyMaterialObject()  {

        List<LinkEntity> linkEntities = List.of(linkEntity);
        Page<LinkEntity> pageLinks = new PageImpl<>(linkEntities);
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findLinksByStudyMaterialId(ID, pageable)).thenReturn(pageLinks);


        Page<LinkVO> result = service.findAllLinksInStudyMaterial(ID, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(URL, result.getContent().get(0).getUrl());
        assertEquals(DESCRIPTION, result.getContent().get(0).getDescription());

        verify(repository, times(1)).findLinksByStudyMaterialId(ID, pageable);


    }



}














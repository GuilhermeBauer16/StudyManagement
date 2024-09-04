package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.exception.LinkNotFoundException;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.repository.LinkRepository;
import com.github.guilhermebauer.studymanagement.utils.ValidatorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkTest {

    @Mock
    private LinkRepository repository;

    @Mock
    private LinkEntity linkEntity;
    @Mock
    private LinkVO linkVO;

    @InjectMocks
    private LinkService service;

    private static final String ID = "5f68880e-7356-4c86-a4a9-f8cc16e2ec87";
    private static final String URL = "https://start.spring.io/" ;
    private static final String DESCRIPTION = "Spring Boot Tutorial";

    private static final String LINK_NOT_FOUND = "The link was not found!";

    @BeforeEach
    void setUp(){
        linkEntity = new LinkEntity(ID, URL, DESCRIPTION);
        linkVO = new LinkVO(ID, URL, DESCRIPTION);
    }

    @Test
    void testCreatePersonalizeWorkoutExercise_WhenSaveWorkoutExercise_ShouldReturnPersonalizeWorkoutExerciseObject() throws IllegalAccessException {

        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = mockStatic(ValidatorUtils.class)) {

            mockedValidatorUtils.when(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any(Class.class))).thenAnswer(invocation -> null);
            mockedValidatorUtils.when(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(),  any(Class.class))).thenAnswer(invocation -> null);


            when(repository.save(any(LinkEntity.class))).thenReturn(linkEntity);
            List<LinkEntity> linkEntities = List.of(linkEntity);
            List<LinkEntity> linkEntityList = service.create(linkEntities);

            mockedValidatorUtils.verify(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any(Class.class)));
            mockedValidatorUtils.verify(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any(Class.class)));
            verify(repository).save(any(LinkEntity.class));

            LinkEntity firstLink = linkEntities.getFirst();
            assertNotNull(linkEntityList);
            assertNotNull(firstLink.getId());

            assertEquals(URL, firstLink.getUrl());
            assertEquals(DESCRIPTION, firstLink.getDescription());

        }
    }


    @Test
    void testUpdateLink_WhenLinkWasUpdated_ShouldReturnLinkObject() throws NoSuchFieldException, IllegalAccessException {

        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = mockStatic(ValidatorUtils.class)) {

            String changedUrl = "https://slowgerman.com/2024/04/16/demonstrationen-gegen-rechtsextremismus-sg-270/";
            String changedDescription = "German Content";

            linkEntity.setDescription(changedDescription);
            linkEntity.setUrl(changedUrl);

            mockedValidatorUtils.when(() -> ValidatorUtils.updateFieldIfNotNull(any(LinkEntity.class),
                            any(LinkVO.class), anyString(), any()))
                    .thenAnswer(invocation -> linkEntity);

            mockedValidatorUtils.when(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()))
                    .thenAnswer(invocation -> null);

            when(repository.findById(ID)).thenReturn(Optional.of(linkEntity));
            when(repository.save(any(LinkEntity.class))).thenReturn(linkEntity);

            LinkVO updatedLink = service.update(linkVO);

            mockedValidatorUtils.verify(() -> ValidatorUtils.updateFieldIfNotNull(any(LinkEntity.class), any(LinkVO.class), anyString(), any()));
            mockedValidatorUtils.verify(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any(Class.class)));

            verify(repository, times(1)).findById(anyString());
            verify(repository, times(1)).save(any());

            verify(repository, times(1)).save(any(LinkEntity.class));

            assertNotNull(updatedLink);
            assertNotNull(updatedLink.getId());

            assertEquals(changedUrl, updatedLink.getUrl());
            assertEquals(changedDescription, updatedLink.getDescription());

        }
    }


    @Test
    void testUpdateLink_WhenLinkIsNotInsertIntoDatabase_ShouldThrowLinkNotFoundException() {

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () ->
                service.update(linkVO));

        assertNotNull(exception);
        assertEquals(LinkNotFoundException.ERROR.formatErrorMessage(LINK_NOT_FOUND)
                , exception.getMessage());


    }

    @Test
    void testFindLinkById_WhenLinkIsValid_ShouldReturnLinkObject() throws NoSuchFieldException, IllegalAccessException {

        when(repository.findById(ID)).thenReturn(Optional.of(linkEntity));

        LinkVO linkById = service.findLinkById(ID);

        verify(repository, times(1)).findById(anyString());

        assertNotNull(linkById);
        assertNotNull(linkById.getId());
        assertEquals(URL, linkById.getUrl());
        assertEquals(DESCRIPTION, linkById.getDescription());


    }


    @Test
    void testFindLinkById_WhenLinkIDIsNotInsertIntoDatabase_ShouldThrowLinkNotFoundException() {

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () ->
                service.findLinkById(ID));

        assertNotNull(exception);
        assertEquals(LinkNotFoundException.ERROR.formatErrorMessage(LINK_NOT_FOUND)
                , exception.getMessage());


    }

    @Test
    void testDelete_WhenDeletingPersonalizedWorkoutExercise_thenDoNothing() {

        when(repository.findById(ID)).thenReturn(Optional.of(linkEntity));
        doNothing().when(repository).delete(linkEntity);

        service.delete(ID);
        verify(repository,times(1)).findById(anyString());
        verify(repository,times(1)).delete(any());

    }

    @Test
    void testDelete_WhenLinkIsNotInsertIntoDatabase_ShouldThrowLinkNotFoundException() {

        LinkNotFoundException exception = assertThrows(LinkNotFoundException.class, () ->
                service.delete(ID));

        assertNotNull(exception);
        assertEquals(LinkNotFoundException.ERROR.formatErrorMessage(LINK_NOT_FOUND)
                , exception.getMessage());


    }


}

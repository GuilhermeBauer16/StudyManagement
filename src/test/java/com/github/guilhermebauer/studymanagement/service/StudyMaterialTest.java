package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.exception.StudyMaterialNotFoundException;
import com.github.guilhermebauer.studymanagement.exception.UserNotFoundException;
import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import com.github.guilhermebauer.studymanagement.model.UserEntity;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.repository.StudyMaterialRepository;
import com.github.guilhermebauer.studymanagement.repository.UserRepository;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.StudyMaterialUpdateRequest;
import com.github.guilhermebauer.studymanagement.response.StudyMaterialResponse;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

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

    private UserEntity userEntity;

    private static final String STUDY_MATERIAL_NOT_FOUND = "The study material was not found";
    private static final String USER_NOT_FOUND_MESSAGE = "That User was not found";


    private static final String INVALID_ID = "5f68880";
    private static final String ID = "5f68880e-7356-4c86-a4a9-f8cc16e2ec87";
    private static final String URL = "https://start.spring.io/";
    private static final String DESCRIPTION = "Spring Boot Tutorial";
    private static final String TITLE = "Introduction to Spring Boot";
    private static final String UPDATED_CONTENT = "A Spring Boot guide";
    private static final String UPDATED_TITLE = "Spring Boot introduction";
    private static final String CONTENT = "This is a comprehensive guide to Spring Boot.";
    private static final String COURSE_TITLE = "Math";
    private static final String COURSE_DESCRIPTION = "Math is a discipline that work with numbers";

    private static final String USER_NAME = "john";
    private static final String EMAIL = "jonhDoe@gmail.com";
    private static final String PASSWORD = "123456";
    private static final String USER_ROLE = "ROLE_USER";
    private static final Set<RoleEntity> ROLES = new HashSet<>(Set.of(new RoleEntity(ID, USER_ROLE)));


    @BeforeEach
    void setUp() {
        userEntity = new UserEntity(ID, USER_NAME, EMAIL, PASSWORD, ROLES);
        linkVO = new LinkVO(ID, URL, DESCRIPTION);
        linkEntity = new LinkEntity(ID, URL, DESCRIPTION);
        courseEntity = new CourseEntity(ID, COURSE_TITLE, COURSE_DESCRIPTION, userEntity);
        studyMaterialVO = new StudyMaterialVO(ID, TITLE, CONTENT, courseEntity, List.of(linkEntity), userEntity);
        studyMaterialEntity = new StudyMaterialEntity(ID, TITLE, CONTENT, courseEntity, List.of(linkEntity), userEntity);
        singleLinkToStudyMaterialRequest = new SingleLinkToStudyMaterialRequest(ID, linkVO);
        linkListToStudyMaterialRequest = new LinkListToStudyMaterialRequest(ID, List.of(linkEntity));
        studyMaterialUpdateRequest = new StudyMaterialUpdateRequest(ID, TITLE, CONTENT);

        SecurityContextHolder.setContext(securityContext);

    }

    @Test
    void testCreateStudyMaterial_WhenCreateStudyMaterial_ShouldReturnStudyMaterialObject() {

        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = Mockito.mockStatic(ValidatorUtils.class)) {

            List<LinkEntity> linkEntities = List.of(linkEntity);
            mockedValidatorUtils.when(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any()))
                    .thenAnswer(invocation -> null);

            mockedValidatorUtils.when(() -> ValidatorUtils.updateFieldIfNotNull(any(StudyMaterialEntity.class),
                            any(StudyMaterialUpdateRequest.class), anyString(), any()))
                    .thenAnswer(invocation -> studyMaterialEntity);

            mockedValidatorUtils.when(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()))
                    .thenAnswer(invocation -> null);

            when(linkService.create(linkEntities)).thenReturn(linkEntities);
            when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(userEntity));
            when(repository.save(any(StudyMaterialEntity.class))).thenReturn(studyMaterialEntity);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(userDetails);
            when(userDetails.getUsername()).thenReturn(EMAIL);

            StudyMaterialResponse createdStudyMaterial = service.create(studyMaterialVO);

            verify(repository, times(1)).save(any(StudyMaterialEntity.class));
            verify(linkService, times(1)).create(anyList());

            mockedValidatorUtils.verify(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any()));
            mockedValidatorUtils.verify(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()));

            assertNotNull(createdStudyMaterial);
            assertNotNull(createdStudyMaterial.getId());
            assertEquals(CONTENT, createdStudyMaterial.getContent());
            assertEquals(TITLE, createdStudyMaterial.getTitle());
            assertEquals(COURSE_TITLE, createdStudyMaterial.getCourseResponse().getTitle());
            assertEquals(COURSE_DESCRIPTION, createdStudyMaterial.getCourseResponse().getDescription());

        }
    }

    @Test
    void testCreateStudyMaterial_WhenStudyMaterialIsNull_ShouldThrowStudyMaterialNotFoundException() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(EMAIL);
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                service.create(null));

        assertNotNull(exception);
        assertEquals(UserNotFoundException.ERROR.formatErrorMessage(USER_NOT_FOUND_MESSAGE)
                , exception.getMessage());


    }



    @Test
    void testUpdateStudyMaterial_WhenUpdatedStudyMaterial_ShouldReturnStudyMaterialObject() {
        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = Mockito.mockStatic(ValidatorUtils.class)) {

            studyMaterialEntity.setContent(UPDATED_CONTENT);
            studyMaterialEntity.setTitle(UPDATED_TITLE);

            mockedValidatorUtils.when(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any()))
                    .thenAnswer(invocation -> null);

            mockedValidatorUtils.when(() -> ValidatorUtils.updateFieldIfNotNull(any(StudyMaterialEntity.class),
                            any(StudyMaterialUpdateRequest.class), anyString(), any()))
                    .thenAnswer(invocation -> studyMaterialEntity);

            mockedValidatorUtils.when(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()))
                    .thenAnswer(invocation -> null);

            when(repository.findByIdAndUserEmail(anyString(), anyString())).thenReturn(Optional.of(studyMaterialEntity));
            when(repository.save(any(StudyMaterialEntity.class))).thenReturn(studyMaterialEntity);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(userDetails);
            when(userDetails.getUsername()).thenReturn(EMAIL);

            StudyMaterialUpdateResponse update = service.update(studyMaterialUpdateRequest);

            verify(repository, times(1)).save(any());
            verify(repository, times(1)).findByIdAndUserEmail(anyString(), anyString());

            mockedValidatorUtils.verify(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any()));
            mockedValidatorUtils.verify(() -> ValidatorUtils.updateFieldIfNotNull(any(), any(), anyString(), any()));
            mockedValidatorUtils.verify(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()));

            assertNotNull(update);
            assertNotNull(update.getId());
            assertEquals(UPDATED_CONTENT, update.getContent());
            assertEquals(UPDATED_TITLE, update.getTitle());

        }
    }

    @Test
    void testUpdateStudyMaterial_WhenStudyMaterialIdIsNotInsertIntoDatabase_ShouldThrowStudyMaterialNotFoundException() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(EMAIL);
        StudyMaterialNotFoundException exception = assertThrows(StudyMaterialNotFoundException.class, () ->
                service.update(studyMaterialUpdateRequest));

        assertNotNull(exception);
        assertEquals(StudyMaterialNotFoundException.ERROR.formatErrorMessage(STUDY_MATERIAL_NOT_FOUND)
                , exception.getMessage());


    }

    @Test
    void testFindStudyMaterial_WhenFindStudyMaterialById_ShouldReturnStudyMaterialObject() {


        when(repository.findByIdAndUserEmail(anyString(),anyString())).thenReturn(Optional.of(studyMaterialEntity));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(EMAIL);

        StudyMaterialResponse studyMaterialByID = service.findByID(ID);

        verify(repository, times(1)).findByIdAndUserEmail(anyString(),anyString());

        assertNotNull(studyMaterialByID);
        assertNotNull(studyMaterialByID.getId());
        assertEquals(CONTENT, studyMaterialByID.getContent());
        assertEquals(TITLE, studyMaterialByID.getTitle());
        assertEquals(COURSE_TITLE, studyMaterialByID.getCourseResponse().getTitle());
        assertEquals(COURSE_DESCRIPTION, studyMaterialByID.getCourseResponse().getDescription());


    }

    @Test
    void testFindStudyMaterialById_WhenStudyMaterialIsNotInsertIntoDatabase_ShouldThrowStudyMaterialNotFoundException() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(EMAIL);
        StudyMaterialNotFoundException exception = assertThrows(StudyMaterialNotFoundException.class, () ->
                service.findByID(ID));

        assertNotNull(exception);
        assertEquals(StudyMaterialNotFoundException.ERROR.formatErrorMessage(STUDY_MATERIAL_NOT_FOUND)
                , exception.getMessage());


    }

    @Test
    void testStudyMaterial_When_FindAll_ShouldReturnStudyMaterialList() {

        List<StudyMaterialEntity> studyMaterialEntities = List.of(studyMaterialEntity);

        when(repository.findAllByUserEmail(anyString(), any(Pageable.class))).thenReturn(new PageImpl<>(studyMaterialEntities));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(EMAIL);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<StudyMaterialResponse> foundedListOfStudyMaterialVO = service.findAll(pageRequest);

        verify(repository, times(1)).findAllByUserEmail(anyString(), any(Pageable.class));

        StudyMaterialResponse foundedStudyMaterialResponse = foundedListOfStudyMaterialVO.getContent().getFirst();

        assertEquals(1, foundedListOfStudyMaterialVO.getTotalElements());

        assertNotNull(foundedStudyMaterialResponse);
        assertNotNull(foundedStudyMaterialResponse.getCourseResponse());

        assertEquals(CONTENT, foundedStudyMaterialResponse.getContent());
        assertEquals(TITLE, foundedStudyMaterialResponse.getTitle());
        assertEquals(COURSE_TITLE, foundedStudyMaterialResponse.getCourseResponse().getTitle());
        assertEquals(COURSE_DESCRIPTION, foundedStudyMaterialResponse.getCourseResponse().getDescription());

    }

    @Test
    void testDelete_WhenDeletingStudyMaterial_thenDoNothing() {

        when(repository.findByIdAndUserEmail(ID, EMAIL)).thenReturn(Optional.of(studyMaterialEntity));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(EMAIL);
        doNothing().when(repository).delete(studyMaterialEntity);
        service.delete(ID);
        verify(repository, times(1)).delete(any(StudyMaterialEntity.class));
    }


    @Test
    void testDelete_WhenStudyMaterialIdNotFoundIntoDatabase_ShouldThrowStudyMaterialNotFoundException() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(EMAIL);
        StudyMaterialNotFoundException exception = assertThrows(StudyMaterialNotFoundException.class, () ->
                service.delete(ID));

        assertNotNull(exception);
        assertEquals(StudyMaterialNotFoundException.ERROR.formatErrorMessage(STUDY_MATERIAL_NOT_FOUND)
                , exception.getMessage());
    }

    @Test
    void testAddLinkInStudyMaterial_WhenCreateStudyMaterial_ShouldReturnStudyMaterialObject() {

        List<LinkEntity> linkEntities = List.of(linkEntity);

        when(repository.findById(anyString())).thenReturn(Optional.of(studyMaterialEntity));
        when(linkService.create(linkEntities)).thenReturn(linkEntities);
        when(repository.save(any(StudyMaterialEntity.class))).thenReturn(studyMaterialEntity);

        StudyMaterialResponse addLinksToStudyMaterial = service.addLinkInStudyMaterial(linkListToStudyMaterialRequest);

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
    void testUpdateLinkInStudyMaterial_WhenUpdatedStudyMaterial_ShouldReturnStudyMaterialObject() {


        when(repository.findById(anyString())).thenReturn(Optional.of(studyMaterialEntity));
        when(repository.save(any(StudyMaterialEntity.class))).thenReturn(studyMaterialEntity);

        StudyMaterialResponse updateLinksToStudyMaterial = service.updateLinkInStudyMaterial(singleLinkToStudyMaterialRequest);

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

        verify(repository, times(1)).findById(anyString());
        verify(repository, times(1)).save(any());


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
        verify(repository, times(1)).findById(anyString());
        assertEquals(1, studyMaterialEntity.getLinks().size());
    }

    @Test
    void testFindAlLLinkInStudyMaterial_WhenFinalAlLStudyMaterial_ShouldReturnStudyMaterialObject() {

        List<LinkEntity> linkEntities = List.of(linkEntity);
        Page<LinkEntity> pageLinks = new PageImpl<>(linkEntities);
        Pageable pageable = PageRequest.of(0, 10);

        when(repository.findLinksByStudyMaterialId(ID, pageable)).thenReturn(pageLinks);


        Page<LinkVO> result = service.findAllLinksInStudyMaterial(ID, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(URL, result.getContent().getFirst().getUrl());
        assertEquals(DESCRIPTION, result.getContent().getFirst().getDescription());

        verify(repository, times(1)).findLinksByStudyMaterialId(ID, pageable);


    }


}














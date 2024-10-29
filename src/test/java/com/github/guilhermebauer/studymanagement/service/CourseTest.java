package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.exception.CourseNotFoundException;
import com.github.guilhermebauer.studymanagement.exception.UserNotFoundException;
import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.UserEntity;
import com.github.guilhermebauer.studymanagement.model.values.CourseVO;
import com.github.guilhermebauer.studymanagement.repository.CourseRepository;
import com.github.guilhermebauer.studymanagement.repository.UserRepository;
import com.github.guilhermebauer.studymanagement.response.CourseResponse;
import com.github.guilhermebauer.studymanagement.utils.ValidatorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseTest {

    private static final String COURSE_NOT_FOUND_MESSAGE =
            "That course was not fonded!";

    private static final String USER_NOT_FOUND_MESSAGE = "That User was not found";

    @Mock
    private CourseRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    private CourseVO courseVO;
    private CourseEntity courseEntity;

    private UserEntity userEntity;


    @InjectMocks
    private CourseService service;




    private static final String ID = "d8e7df81-2cd4-41a2-a005-62e6d8079716";
    private static final String TITLE = "Math";
    private static final String DESCRIPTION = "Math is a discipline that work with numbers";
    private static final String UPDATED_DESCRIPTION = "Physical is a discipline that work with numbers";
    private static final String UPDATED_TITLE = "Physical";

    private static final String USER_NAME = "john";
    private static final String EMAIL = "jonhDoe@gmail.com";
    private static final String PASSWORD = "123456";
    private static final String USER_ROLE = "ROLE_USER";
    private static final Set<RoleEntity> ROLES = new HashSet<>(Set.of(new RoleEntity(ID, USER_ROLE)));


    @BeforeEach
    void setUp() {

        userEntity = new UserEntity(ID, USER_NAME, EMAIL, PASSWORD, ROLES);
        courseVO = new CourseVO(ID, TITLE, DESCRIPTION, userEntity);
        courseEntity = new CourseEntity(ID, TITLE, DESCRIPTION, userEntity);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(EMAIL);

        SecurityContextHolder.setContext(securityContext);

    }

    @Test
    void testCreateCourse_WhenSaveCourse_ShouldReturnCourseObject() throws NoSuchFieldException, IllegalAccessException {

        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = mockStatic(ValidatorUtils.class)) {
            mockedValidatorUtils.when(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any())).thenAnswer(invocation -> null);
            mockedValidatorUtils.when(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any())).thenAnswer(invocation -> null);

            when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(userEntity));
            when(repository.save(any(CourseEntity.class))).thenReturn(courseEntity);

            CourseResponse savedCourse = service.create(courseVO);

            mockedValidatorUtils.verify(() -> ValidatorUtils.checkObjectIsNullOrThrowException(any(), anyString(), any()));
            mockedValidatorUtils.verify(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()));
            verify(repository, times(1)).save(any(CourseEntity.class));

            assertNotNull(savedCourse);
            assertNotNull(savedCourse.getId());

            assertEquals(TITLE, savedCourse.getTitle());
            assertEquals(DESCRIPTION, savedCourse.getDescription());

        }
    }

    @Test
    void testCreate_WhenUserIsNotFound_ShouldThrowUserNotFoundException() {

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                service.create(courseVO));

        assertNotNull(exception);
        assertEquals(UserNotFoundException.ERROR.formatErrorMessage(USER_NOT_FOUND_MESSAGE), exception.getMessage());


    }


    @Test
    void testUpdateCourse_WhenCourseWasUpdated_ShouldReturnCourseObject() throws NoSuchFieldException, IllegalAccessException {

        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = mockStatic(ValidatorUtils.class)) {
            courseEntity.setTitle(UPDATED_TITLE);
            courseEntity.setDescription(UPDATED_DESCRIPTION);
            mockedValidatorUtils.when(() -> ValidatorUtils.updateFieldIfNotNull(any(CourseEntity.class), any(), anyString(), any()))
                    .thenAnswer(invocation -> courseEntity);
            mockedValidatorUtils.when(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any())).thenAnswer(invocation -> null);

            when(repository.findByIdAndUserEmail(ID,EMAIL)).thenReturn(Optional.of(courseEntity));
            when(repository.save(any(CourseEntity.class))).thenReturn(courseEntity);

            CourseResponse updatedCourse = service.update(courseVO);

            mockedValidatorUtils.verify(() -> ValidatorUtils.updateFieldIfNotNull(any(CourseEntity.class), any(CourseVO.class), anyString(), any()));
            mockedValidatorUtils.verify(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any()));
            verify(repository, times(1)).findByIdAndUserEmail(anyString(),anyString());
            verify(repository, times(1)).save(any());

            verify(repository, times(1)).save(any(CourseEntity.class));

            assertNotNull(updatedCourse);
            assertNotNull(updatedCourse.getId());

            assertEquals(UPDATED_TITLE, updatedCourse.getTitle());
            assertEquals(UPDATED_DESCRIPTION, updatedCourse.getDescription());

        }
    }

    @Test
    void testUpdate_WhenCourseIDIsNull_ShouldThrowCourseNotFoundException() {
        when(repository.findByIdAndUserEmail(ID,EMAIL)).thenReturn(Optional.empty());
        CourseNotFoundException exception = assertThrows(CourseNotFoundException.class, () ->
                service.update(courseVO));

        assertNotNull(exception);
        assertEquals(CourseNotFoundException.ERROR.formatErrorMessage(COURSE_NOT_FOUND_MESSAGE), exception.getMessage());


    }

    @Test
    void testFindCourseById_WhenCourseWasFound_ShouldReturnCourseObject() throws NoSuchFieldException, IllegalAccessException {

        when(repository.findByIdAndUserEmail(ID,EMAIL)).thenReturn(Optional.of(courseEntity));
        CourseResponse byId = service.findById(ID);
        verify(repository, times(1)).findByIdAndUserEmail(anyString(),anyString());
        assertNotNull(byId);
        assertNotNull(byId.getId());

        assertEquals(TITLE, byId.getTitle());
        assertEquals(DESCRIPTION, byId.getDescription());
    }

    @Test
    void testFindCourseById_WhenCourseIsNotRegisterInDatabase_ShouldThrowCourseNotFoundException() {

        CourseNotFoundException exception = assertThrows(CourseNotFoundException.class, () ->
                service.findById(ID));

        assertNotNull(exception);
        assertEquals(CourseNotFoundException.ERROR.formatErrorMessage(COURSE_NOT_FOUND_MESSAGE), exception.getMessage());

    }

    @Test
    void testFindCourseByTitle_WhenCourseWasFound_ShouldReturnPagedCoursesObjects(){
        List<CourseEntity> courseEntityList = List.of(courseEntity);

        when(repository.findByTitleAndEmail(anyString(), any(Pageable.class), anyString())).thenReturn(new PageImpl<>(courseEntityList));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<CourseResponse> courseByTitle = service.findCourseByTitle(TITLE, pageRequest);

        verify(repository, times(1)).findByTitleAndEmail(anyString(), any(Pageable.class), anyString());

        CourseResponse courseResponseRecovered = courseByTitle.getContent().getFirst();

        assertNotNull(courseByTitle);
        assertEquals(1, courseByTitle.getTotalElements());
        assertNotNull(courseResponseRecovered.getId());
        assertEquals(TITLE, courseResponseRecovered.getTitle());
        assertEquals(DESCRIPTION, courseResponseRecovered.getDescription());

    }

    @Test
    void testFindAll_WhenAllCourseWasFound_ShouldReturnPagedCoursesObjects() throws NoSuchFieldException, IllegalAccessException {
        List<CourseEntity> courseEntityList = List.of(courseEntity);

        when(repository.findAllByUserEmail(anyString(),any(Pageable.class))).thenReturn(new PageImpl<>(courseEntityList));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<CourseResponse> courseByTitle = service.findAll(pageRequest);

        verify(repository, times(1)).findAllByUserEmail(anyString(),any(Pageable.class));

        CourseResponse courseResponseRecovered = courseByTitle.getContent().getFirst();

        assertNotNull(courseByTitle);
        assertEquals(1, courseByTitle.getTotalElements());
        assertNotNull(courseResponseRecovered.getId());
        assertEquals(TITLE, courseResponseRecovered.getTitle());
        assertEquals(DESCRIPTION, courseResponseRecovered.getDescription());

    }

    @Test
    void testDelete_WhenDeleteCourse_thenDoNothing() {
        when(repository.findByIdAndUserEmail(ID,EMAIL)).thenReturn(Optional.of(courseEntity));
        doNothing().when(repository).delete(courseEntity);
        service.delete(ID);
        verify(repository, times(1)).delete(any(CourseEntity.class));
    }

    @Test
    void testDelete_WhenCourseIsNotRegisterInDatabase_ShouldThrowCourseNotFoundException() {

        CourseNotFoundException exception = assertThrows(CourseNotFoundException.class, () ->
                service.delete(ID));

        assertNotNull(exception);
        assertEquals(CourseNotFoundException.ERROR.formatErrorMessage(COURSE_NOT_FOUND_MESSAGE), exception.getMessage());

    }


}

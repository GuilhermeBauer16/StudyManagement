package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.exception.CourseNotFoundException;
import com.github.guilhermebauer.studymanagement.exception.FieldNotFound;
import com.github.guilhermebauer.studymanagement.exception.UserNotFoundException;
import com.github.guilhermebauer.studymanagement.factory.CourseFactory;
import com.github.guilhermebauer.studymanagement.mapper.BuildMapper;
import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.UserEntity;
import com.github.guilhermebauer.studymanagement.model.values.CourseVO;
import com.github.guilhermebauer.studymanagement.repository.CourseRepository;
import com.github.guilhermebauer.studymanagement.repository.UserRepository;
import com.github.guilhermebauer.studymanagement.response.CourseResponse;
import com.github.guilhermebauer.studymanagement.service.contract.CourseServiceContract;
import com.github.guilhermebauer.studymanagement.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService implements CourseServiceContract {

    private static final String COURSE_NOT_FOUND_MESSAGE =
            "That course was not fonded!";

    private static final String USER_NOT_FOUND_MESSAGE = "That User was not found";

    private final CourseRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public CourseService(CourseRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public CourseResponse create(CourseVO courseVO) throws NoSuchFieldException, IllegalAccessException {

        UserEntity userEntity = userRepository.findUserByEmail(retrieveUserEmail())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        ValidatorUtils.checkObjectIsNullOrThrowException(courseVO, COURSE_NOT_FOUND_MESSAGE, CourseNotFoundException.class);
        CourseEntity courseEntity = CourseFactory.create(courseVO.getTitle(), courseVO.getDescription(), userEntity);
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(courseEntity, COURSE_NOT_FOUND_MESSAGE, FieldNotFound.class);
        repository.save(courseEntity);
        return BuildMapper.parseObject(new CourseResponse(), courseEntity);
    }

    @Override
    public CourseResponse update(CourseVO courseVO) throws NoSuchFieldException, IllegalAccessException {

        CourseEntity recoveredCourse = repository.findByIdAndUserEmail(courseVO.getId(), retrieveUserEmail())
                .orElseThrow(() -> new CourseNotFoundException(COURSE_NOT_FOUND_MESSAGE));
        CourseEntity courseEntity = ValidatorUtils.updateFieldIfNotNull
                (recoveredCourse, courseVO, COURSE_NOT_FOUND_MESSAGE, CourseNotFoundException.class);

        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(courseEntity, COURSE_NOT_FOUND_MESSAGE, FieldNotFound.class);

        CourseEntity updatedCourseResponse = repository.save(courseEntity);
        return BuildMapper.parseObject(new CourseResponse(), updatedCourseResponse);
    }

    @Override
    public CourseResponse findById(String id) throws NoSuchFieldException, IllegalAccessException {

        CourseEntity courseEntity = repository.findByIdAndUserEmail(id, retrieveUserEmail())
                .orElseThrow(() -> new CourseNotFoundException(COURSE_NOT_FOUND_MESSAGE));

        return BuildMapper.parseObject(new CourseResponse(), courseEntity);
    }

    @Override
    public Page<CourseResponse> findCourseByTitle(String title, Pageable pageable) {

        Page<CourseEntity> courseEntitiesPage = repository.findByTitleAndEmail(title, pageable, retrieveUserEmail());
        return retrieveCourseResponsePage(pageable, courseEntitiesPage);
    }


    @Override
    public Page<CourseResponse> findAll(Pageable pageable) throws NoSuchFieldException, IllegalAccessException {

        Page<CourseEntity> allCourses = repository.findAllByUserEmail(retrieveUserEmail(), pageable);
        return retrieveCourseResponsePage(pageable, allCourses);
    }


    @Override
    public void delete(String id) {

        CourseEntity recoveredCourse = repository.findByIdAndUserEmail(id, retrieveUserEmail())
                .orElseThrow(() -> new CourseNotFoundException(COURSE_NOT_FOUND_MESSAGE));
        repository.delete(recoveredCourse);

    }

    private String retrieveUserEmail() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();

    }

    private PageImpl<CourseResponse> retrieveCourseResponsePage(Pageable pageable, Page<CourseEntity> courseEntityPage) {
        List<CourseEntity> courses = courseEntityPage.getContent();
        List<CourseResponse> courseResponses = new ArrayList<>();
        for (CourseEntity courseEntity : courses) {

            courseResponses.add(BuildMapper.parseObject(new CourseResponse(), courseEntity));
        }

        return new PageImpl<>(courseResponses, pageable, courseEntityPage.getTotalElements());
    }

}

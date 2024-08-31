package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.exception.CourseNotFoundException;
import com.github.guilhermebauer.studymanagement.exception.FieldNotFound;
import com.github.guilhermebauer.studymanagement.factory.CourseFactory;
import com.github.guilhermebauer.studymanagement.mapper.BuildMapper;
import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.values.CourseVO;
import com.github.guilhermebauer.studymanagement.repository.CourseRepository;
import com.github.guilhermebauer.studymanagement.response.CourseResponse;
import com.github.guilhermebauer.studymanagement.service.contract.CourseServiceContract;
import com.github.guilhermebauer.studymanagement.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;    

@Service
public class CourseService implements CourseServiceContract {

    private static final String COURSE_NOT_FOUND_MESSAGE =
            "That course was not fonded!";

    private final CourseRepository repository;
    @Autowired
    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public CourseResponse create(CourseVO courseVO) throws NoSuchFieldException, IllegalAccessException {

        ValidatorUtils.checkObjectIsNullOrThrowException(courseVO,COURSE_NOT_FOUND_MESSAGE, CourseNotFoundException.class);
        CourseEntity courseEntity = CourseFactory.create(courseVO.getTitle(), courseVO.getDescription());
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(courseEntity,COURSE_NOT_FOUND_MESSAGE, FieldNotFound.class);
        repository.save(courseEntity);
        return BuildMapper.parseObject(new CourseResponse(),courseEntity);
    }

    @Override
    public CourseResponse update(CourseVO courseVO) throws NoSuchFieldException, IllegalAccessException {

        CourseEntity recoveredCourse = repository.findById(courseVO.getId()).orElseThrow(() -> new CourseNotFoundException(COURSE_NOT_FOUND_MESSAGE));
        CourseEntity courseEntity = ValidatorUtils.updateFieldIfNotNull(recoveredCourse, courseVO, COURSE_NOT_FOUND_MESSAGE, CourseNotFoundException.class);
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(courseEntity,COURSE_NOT_FOUND_MESSAGE,FieldNotFound.class);
        repository.save(courseEntity);
        return BuildMapper.parseObject(new CourseResponse(),courseEntity);
    }

    @Override
    public CourseResponse findById(String id) throws NoSuchFieldException, IllegalAccessException {

        CourseEntity recoveredCourse = repository.findById(id).orElseThrow(() -> new CourseNotFoundException(COURSE_NOT_FOUND_MESSAGE));
        return BuildMapper.parseObject(new CourseResponse(), recoveredCourse);
    }

    @Override
    public Page<CourseResponse> findCourseByTitle(String title,Pageable pageable) throws NoSuchFieldException, IllegalAccessException {

        Page<CourseEntity> courseEntitiesPage = repository.findByTitle(title, pageable);
        List<CourseEntity> content = courseEntitiesPage.getContent();

        List<CourseResponse> informationResponses = new ArrayList<>();

        for(CourseEntity courseEntity : content){

            informationResponses.add(BuildMapper.parseObject(new CourseResponse(), courseEntity));
        }

        return new PageImpl<>(informationResponses, pageable, courseEntitiesPage.getTotalElements());
    }


    @Override
    public Page<CourseResponse> findAll(Pageable pageable) throws NoSuchFieldException, IllegalAccessException {

        Page<CourseEntity> allCourses = repository.findAll(pageable);
        List<CourseEntity> content = allCourses.getContent();
        List<CourseResponse >informationResponses = new ArrayList<>();
        for(CourseEntity courseEntity : content){

            informationResponses.add(BuildMapper.parseObject(new CourseResponse(), courseEntity));
        }

        return new PageImpl<>(informationResponses, pageable, allCourses.getTotalElements());
    }

    @Override
    public void delete(String id) {

        CourseEntity recoveredCourse = repository.findById(id).orElseThrow(() -> new CourseNotFoundException(COURSE_NOT_FOUND_MESSAGE));
        repository.delete(recoveredCourse);

    }
}

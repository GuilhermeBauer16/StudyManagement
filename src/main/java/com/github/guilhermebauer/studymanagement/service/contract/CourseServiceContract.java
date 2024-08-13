package com.github.guilhermebauer.studymanagement.service.contract;

import com.github.guilhermebauer.studymanagement.model.values.CourseVO;
import com.github.guilhermebauer.studymanagement.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseServiceContract {

    CourseResponse create(CourseVO courseVO) throws InstantiationException, IllegalAccessException, NoSuchFieldException;

    CourseResponse update(CourseVO courseVO) throws NoSuchFieldException, IllegalAccessException;

    CourseResponse findById(String id) throws NoSuchFieldException, IllegalAccessException;

    Page<CourseResponse> findCourseByTitle(String title,Pageable pageable) throws NoSuchFieldException, IllegalAccessException;

    Page<CourseResponse> findAll(final Pageable pageable) throws NoSuchFieldException, IllegalAccessException;

    void delete(String id);
}

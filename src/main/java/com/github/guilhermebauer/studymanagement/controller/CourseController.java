package com.github.guilhermebauer.studymanagement.controller;

import com.github.guilhermebauer.studymanagement.controller.contract.CourseControllerContract;
import com.github.guilhermebauer.studymanagement.model.values.CourseVO;
import com.github.guilhermebauer.studymanagement.response.CourseResponse;
import com.github.guilhermebauer.studymanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
public class CourseController implements CourseControllerContract {


    private final CourseService service;

    @Autowired
    public CourseController(CourseService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<CourseResponse> create(CourseVO courseVO) throws IllegalAccessException, NoSuchFieldException {
        CourseResponse createdCourse = service.create(courseVO);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CourseResponse> update(CourseVO courseVO) throws NoSuchFieldException, IllegalAccessException {
        CourseResponse updatedCourse = service.update(courseVO);
        return ResponseEntity.ok(updatedCourse);
    }

    @Override
    public ResponseEntity<CourseResponse> findById(String id) throws NoSuchFieldException, IllegalAccessException {
        CourseResponse courseById = service.findById(id);
        return ResponseEntity.ok(courseById);
    }

    @Override
    public ResponseEntity<Page<CourseResponse>> findCourseByTitle(String title,Pageable pageable) throws NoSuchFieldException, IllegalAccessException {
        Page<CourseResponse> courseByTitle = service.findCourseByTitle(title,pageable);
        return ResponseEntity.ok(courseByTitle);
    }


    @Override
    public ResponseEntity<Page<CourseResponse>> findAll(Pageable pageable) throws NoSuchFieldException, IllegalAccessException {

        Page<CourseResponse> allCourse = service.findAll(pageable);
        return ResponseEntity.ok(allCourse);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

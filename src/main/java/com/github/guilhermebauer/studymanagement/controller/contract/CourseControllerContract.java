package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.model.values.CourseVO;
import com.github.guilhermebauer.studymanagement.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CourseControllerContract {
    @PostMapping
    ResponseEntity<CourseResponse> create(@RequestBody CourseVO courseVO) throws InstantiationException, IllegalAccessException, NoSuchFieldException;

    @PutMapping
    ResponseEntity<CourseResponse>  update(@RequestBody CourseVO courseVO) throws NoSuchFieldException, IllegalAccessException;

    @GetMapping(value = "/{id}")
    ResponseEntity<CourseResponse>  findById(@PathVariable("id") String id) throws NoSuchFieldException, IllegalAccessException;

    @GetMapping(value = "findByTitle/{title}")
    ResponseEntity<Page<CourseResponse>> findCourseByTitle(@PathVariable("title")String title,Pageable pageable) throws NoSuchFieldException, IllegalAccessException;

    ResponseEntity<Page<CourseResponse>> findAll(final Pageable pageable) throws NoSuchFieldException, IllegalAccessException;
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") String id);
}

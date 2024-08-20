package com.github.guilhermebauer.studymanagement.service.contract;

import com.github.guilhermebauer.studymanagement.model.values.CourseVO;
import com.github.guilhermebauer.studymanagement.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for managing course operations.
 */
public interface CourseServiceContract {

    /**
     * Creates a new course.
     *
     * @param courseVO the course value object containing the course details.
     * @return the created course response.
     * @throws InstantiationException if an instantiation problem occurs.
     * @throws IllegalAccessException if an access problem occurs.
     * @throws NoSuchFieldException   if a required field is missing.
     * @see CourseResponse
     * @see CourseVO
     */
    CourseResponse create(CourseVO courseVO) throws InstantiationException, IllegalAccessException, NoSuchFieldException;

    /**
     * Updates an existing course.
     *
     * @param courseVO the course value object containing the updated course details.
     * @return the updated course response.
     * @throws NoSuchFieldException   if a required field is missing.
     * @throws IllegalAccessException if an access problem occurs.
     * @see CourseResponse
     * @see CourseVO
     */
    CourseResponse update(CourseVO courseVO) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Finds a course by its ID.
     *
     * @param id the ID of the course to find.
     * @return the course response found by the given ID.
     * @throws NoSuchFieldException   if a required field is missing.
     * @throws IllegalAccessException if an access problem occurs.
     * @see CourseResponse
     * @see CourseVO
     */
    CourseResponse findById(String id) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Finds courses by their title.
     *
     * @param title    the title of the course(s) to find.
     * @param pageable the pagination information.
     * @return a pageable list of course responses matching the given title.
     * @throws NoSuchFieldException   if a required field is missing.
     * @throws IllegalAccessException if an access problem occurs.
     * @see Pageable
     */
    Page<CourseResponse> findCourseByTitle(String title, Pageable pageable) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Finds all courses.
     *
     * @param pageable the pagination information.
     * @return a pageable list of all course responses.
     * @throws NoSuchFieldException   if a required field is missing.
     * @throws IllegalAccessException if an access problem occurs.
     * @see Pageable
     */
    Page<CourseResponse> findAll(Pageable pageable) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Deletes a course by its ID.
     *
     * @param id the ID of the course to delete.
     */
    void delete(String id);
}
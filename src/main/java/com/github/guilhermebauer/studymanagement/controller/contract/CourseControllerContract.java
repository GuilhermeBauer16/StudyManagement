package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.model.values.CourseVO;
import com.github.guilhermebauer.studymanagement.response.CourseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Interface that defines the contract for Course-related operations in the Study Management system.
 * This interface is used to manage courses, including creating, updating, finding, and deleting them.
 * All methods return a {@link ResponseEntity} containing the relevant response or status.
 */
public interface CourseControllerContract {

    /**
     * Creates a new course in the system.
     *
     * @param courseVO the course value object containing the details of the course to be created.
     * @return a {@link ResponseEntity} containing the created {@link CourseResponse}.
     * @throws InstantiationException if there is an error during instantiation.
     * @throws IllegalAccessException if the method is unable to access the necessary fields.
     * @throws NoSuchFieldException   if a required field is not found.
     * @see CourseResponse
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a new Course",
            description = "Creates a new course and returns the created course.",
            tags = "Courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "404", description = "Course Not Found or Field Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<CourseResponse> create(@RequestBody CourseVO courseVO) throws InstantiationException, IllegalAccessException, NoSuchFieldException;

    /**
     * Updates an existing course in the system.
     *
     * @param courseVO the course value object containing the updated details of the course.
     * @return a {@link ResponseEntity} containing the updated {@link CourseResponse}.
     * @throws NoSuchFieldException   if a required field is not found.
     * @throws IllegalAccessException if the method is unable to access the necessary fields.
     * @see CourseResponse
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a Course",
            description = "Updates an existing course and returns the updated course.",
            tags = "Courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "404", description = "Course Not Found or Field Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<CourseResponse> update(@RequestBody CourseVO courseVO) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Finds a course by its ID.
     *
     * @param id the ID of the course to be found.
     * @return a {@link ResponseEntity} containing the found {@link CourseResponse}.
     * @throws NoSuchFieldException   if a required field is not found.
     * @throws IllegalAccessException if the method is unable to access the necessary fields.
     * @see CourseResponse
     */
    @GetMapping(value = "/{id}")
    @Operation(summary = "Find a single Course",
            description = "Finds a course by its ID and returns it.",
            tags = "Courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "404", description = "Course Not Found or Field Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<CourseResponse> findById(@PathVariable("id") String id) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Finds courses by their title.
     *
     * @param title    the title of the courses to be found.
     * @param pageable pagination information for the results.
     * @return a {@link ResponseEntity} containing a paginated list of {@link CourseResponse}.
     * @throws NoSuchFieldException   if a required field is not found.
     * @throws IllegalAccessException if the method is unable to access the necessary fields.
     * @see CourseResponse
     * @see Pageable
     */
    @GetMapping(value = "findByTitle/{title}")
    @Operation(summary = "Find Courses by the title",
            description = "Finds courses by their title and returns a paginated list.",
            tags = "Courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class)))
    })
    ResponseEntity<Page<CourseResponse>> findCourseByTitle(@PathVariable("title") String title, Pageable pageable) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Finds all courses in the system.
     *
     * @param pageable pagination information for the results.
     * @return a {@link ResponseEntity} containing a paginated list of all {@link CourseResponse}.
     * @throws NoSuchFieldException   if a required field is not found.
     * @throws IllegalAccessException if the method is unable to access the necessary fields.
     * @see CourseResponse
     * @see Pageable
     */
    @GetMapping
    @Operation(summary = "Find All Courses",
            description = "Finds all courses in the system and returns a paginated list.",
            tags = "Courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class)))
    })
    ResponseEntity<Page<CourseResponse>> findAll(@PageableDefault(size = 20, page = 0, sort = "title") Pageable pageable) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Deletes a course by its ID.
     *
     * @param id the ID of the course to be deleted.
     * @return a {@link ResponseEntity} with status indicating the result of the operation.
     */
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a Course",
            description = "Deletes a course by its ID.",
            tags = "Courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = CourseResponse.class))),
            @ApiResponse(responseCode = "404", description = "Course Not Found or Field Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<Void> delete(@PathVariable("id") String id);
}

package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.StudyMaterialUpdateRequest;
import com.github.guilhermebauer.studymanagement.response.StudyMaterialResponse;
import com.github.guilhermebauer.studymanagement.response.StudyMaterialUpdateResponse;
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
 * Interface that defines the contract for Study Material-related operations in the Study Management system.
 * This interface is used to manage study materials through HTTP requests, including creating, updating,
 * finding, and deleting them.
 */
public interface StudyMaterialControllerContract {

    /**
     * Creates a new study material in the system.
     *
     * @param studyMaterialVO the {@link StudyMaterialVO} object containing the details of the study material to be created.
     * @return a {@link ResponseEntity} containing the created {@link StudyMaterialResponse} object.
     * @see StudyMaterialVO
     * @see CourseEntity
     * @see LinkEntity
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a new Study Material",
            description = "Creates a new Study Material and returns the created study material.",
            tags = "Study-Material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = StudyMaterialVO.class))),
            @ApiResponse(responseCode = "404", description = "StudyMaterial Not Found or Field Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<StudyMaterialResponse> create(@RequestBody StudyMaterialVO studyMaterialVO);

    /**
     * Updates an existing study material in the system.
     *
     * @param request the {@link StudyMaterialUpdateRequest} object containing the updated details of the study material.
     * @return a {@link ResponseEntity} containing the updated {@link StudyMaterialUpdateResponse} object.
     * @see StudyMaterialUpdateRequest
     * @see StudyMaterialUpdateResponse
     * @see LinkEntity
     * @see CourseEntity
     * @see StudyMaterialVO
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a Study Material",
            description = "Update Study Material and returns the updated study material.",
            tags = "Study-Material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = StudyMaterialUpdateResponse.class))),
            @ApiResponse(responseCode = "404", description = "StudyMaterial Not Found or Field Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<StudyMaterialUpdateResponse> update(@RequestBody StudyMaterialUpdateRequest request);

    /**
     * Finds a study material by its ID.
     *
     * @param id the ID of the study material to be found.
     * @return a {@link ResponseEntity} containing the {@link StudyMaterialResponse} object with the specified ID.
     * @see StudyMaterialVO
     * @see LinkEntity
     * @see CourseEntity

     */
    @GetMapping(value = "/{id}")
    @Operation(summary = "Find a single Study material",
            description = "Finds a Study material by its ID and returns it.",
            tags = "Study-Material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = StudyMaterialVO.class))),
            @ApiResponse(responseCode = "404", description = "StudyMaterial Not Found or Field Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<StudyMaterialResponse> findById(@PathVariable(value = "id") String id);

    /**
     * Finds all study materials in the system with pagination.
     *
     * @param pageable the pagination information for the results.
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link StudyMaterialResponse} objects.
     * @see Pageable
     * @see StudyMaterialVO
     * @see LinkEntity
     * @see CourseEntity
     */
    @GetMapping
    @Operation(summary = "Find All Study material",
            description = "Finds all Study material in the system and returns a paginated list.",
            tags = "Study-Material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })

    ResponseEntity<Page<StudyMaterialResponse>> findAll(@PageableDefault(size = 5) Pageable pageable);

    /**
     * Deletes a study material by its ID.
     *
     * @param id the ID of the study material to be deleted.
     * @return a {@link ResponseEntity} with status indicating the result of the operation.
     * @see LinkEntity
     * @see CourseEntity
     * @see StudyMaterialVO
     */
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a Study material",
            description = "Deletes a Study material by its ID.",
            tags = "Study-Material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation, will return a not content"),

            @ApiResponse(responseCode = "404", description = "Study Material Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<Void> delete(@PathVariable(value = "id") String id);

    /**
     * Adds a list of links to a study material.
     *
     * @param request the {@link LinkListToStudyMaterialRequest} object containing the list of links to be added.
     * @return a {@link ResponseEntity} containing the updated {@link StudyMaterialResponse} object with the added links.
     * @see LinkListToStudyMaterialRequest
     * @see StudyMaterialVO
     * @see LinkEntity
     * @see CourseEntity
     * @see StudyMaterialVO
     */
    @PostMapping(value = "/addLinks",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add a link into a Study material",
            description = "Add a links into a Study material, the study material will be retrieved by ID",
            tags = "Study-Material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = StudyMaterialVO.class))),
            @ApiResponse(responseCode = "404", description = "Study Material Not Found, Link Not Found or Field Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<StudyMaterialResponse> addLinkInStudyMaterial(@RequestBody LinkListToStudyMaterialRequest request);

    /**
     * Updates a single link in a study material.
     *
     * @param request the {@link SingleLinkToStudyMaterialRequest} object containing the link details to be updated.
     * @return a {@link ResponseEntity} containing the updated {@link StudyMaterialResponse} object with the updated link.
     * @see SingleLinkToStudyMaterialRequest
     * @see StudyMaterialVO
     * @see LinkEntity
     * @see CourseEntity
     */
    @PutMapping(value = "/updateLink",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates a single link in a study material",
            description = "Updates a single link in a study material,the study material will be retrieved by ID",
            tags = "Study-Material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = StudyMaterialVO.class))),
            @ApiResponse(responseCode = "404", description = "Study Material Not Found, Link Not Found or Field Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<StudyMaterialResponse> updateLinkInStudyMaterial(@RequestBody SingleLinkToStudyMaterialRequest request);

    /**
     * Finds all links associated with a study material.
     *
     * @param studyMaterialId the ID of the study material whose links are to be found.
     * @param pageable        the pagination information for the results.
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link LinkVO} objects associated with the specified study material.
     * @see LinkVO
     * @see Pageable
     * @see LinkEntity
     * @see CourseEntity
     * @see StudyMaterialVO
     */
    @GetMapping(value = "/findAllLinks/{id}")
    @Operation(summary = "Finds all links associated with a study material",
            description = "Finds all links associated with a study material. The study material will be retrieved by ID",
            tags = "Study-Material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = LinkVO.class))),
            @ApiResponse(responseCode = "404", description = "Study Material Not Found or Link Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<Page<LinkVO>> findAllLinksInStudyMaterial(@PathVariable(value = "id") String studyMaterialId, Pageable pageable);

    /**
     * Deletes a single link from a study material.
     *
     * @param request the {@link SingleLinkToStudyMaterialRequest} object containing the link details to be deleted.
     * @return a {@link ResponseEntity} with status indicating the result of the operation.
     * @see SingleLinkToStudyMaterialRequest
     * @see LinkEntity
     * @see CourseEntity
     * @see StudyMaterialVO
     */
    @DeleteMapping(value = "/deleteLinks")
    @Operation(summary = "Deletes a single link from a study material.",
            description = "Deletes a single link from a study material.. The study material will be retrieved by ID",
            tags = "Study-Material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation, will return a not content",
                    content = @Content(schema = @Schema(implementation = LinkVO.class))),
            @ApiResponse(responseCode = "404", description = "Study Material Not Found or Link Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })

    ResponseEntity<Void> deleteLinkInStudyMaterial(@RequestBody SingleLinkToStudyMaterialRequest request);
}

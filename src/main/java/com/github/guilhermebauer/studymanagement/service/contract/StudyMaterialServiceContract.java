package com.github.guilhermebauer.studymanagement.service.contract;


import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.StudyMaterialUpdateRequest;
import com.github.guilhermebauer.studymanagement.response.StudyMaterialResponse;
import com.github.guilhermebauer.studymanagement.response.StudyMaterialUpdateResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;

/**
 * Interface for managing study material operations.
 */
public interface StudyMaterialServiceContract {

    /**
     * Creates a new study material.
     *
     * @param studyMaterialVO the {@link StudyMaterialVO} object containing the details of the study material to be created.
     * @return the created {@link StudyMaterialResponse} object.
     * @see StudyMaterialVO
     */
    StudyMaterialResponse create(StudyMaterialVO studyMaterialVO);

    /**
     * Updates an existing study material.
     *
     * @param request the {@link StudyMaterialUpdateRequest} object containing the updated details of the study material.
     * @return the updated {@link StudyMaterialUpdateResponse} object.
     * @see StudyMaterialUpdateRequest
     * @see StudyMaterialUpdateResponse
     */
    StudyMaterialUpdateResponse update(StudyMaterialUpdateRequest request);

    /**
     * Finds a study material by its ID.
     *
     * @param id the ID of the study material to find.
     * @return the {@link StudyMaterialResponse} object with the specified ID.
     * @see StudyMaterialVO
     */
    StudyMaterialResponse findByID(String id);


    /**
     * Finds all study materials with pagination.
     *
     * @param pageable the pagination information.
     * @return a pageable list of {@link StudyMaterialResponse} objects.
     * @see Pageable
     * @see StudyMaterialVO
     */
    Page<StudyMaterialResponse> findAll(Pageable pageable);

    /**
     * Deletes a study material by its ID.
     *
     * @param id the ID of the study material to delete.
     */
    void delete(String id);

    /**
     * Adds a list of links to a study material.
     *
     * @param request the {@link LinkListToStudyMaterialRequest} object containing the list of links to be added.
     * @return the updated {@link StudyMaterialResponse} object with the added links.
     * @see LinkListToStudyMaterialRequest
     * @see StudyMaterialVO
     * @see LinkVO
     * @see LinkEntity
     */
    StudyMaterialResponse addLinkInStudyMaterial(LinkListToStudyMaterialRequest request);

    /**
     * Updates a single link in a study material.
     *
     * @param request the {@link SingleLinkToStudyMaterialRequest} object containing the link details to be updated.
     * @return the updated {@link StudyMaterialResponse} object with the updated link.
     * @see SingleLinkToStudyMaterialRequest
     * @see StudyMaterialVO
     * @see LinkVO
     * @see LinkEntity
     */
    StudyMaterialResponse updateLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request);

    /**
     * Deletes a single link from a study material.
     *
     * @param request the {@link SingleLinkToStudyMaterialRequest} object containing the link details to be deleted.
     * @see LinkVO
     * @see LinkEntity
     */
    @Transactional
    void deleteLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request);

    /**
     * Finds all links associated with a study material.
     *
     * @param studyMaterialId the ID of the study material whose links are to be found.
     * @param pageable        the pagination information.
     * @return a pageable list of {@link LinkVO} objects associated with the specified study material.
     * @see LinkVO
     * @see Pageable
     */
    Page<LinkVO> findAllLinksInStudyMaterial(String studyMaterialId, Pageable pageable);
}

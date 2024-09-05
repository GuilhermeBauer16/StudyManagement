package com.github.guilhermebauer.studymanagement.service.contract;

import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interface for managing link operations.
 */
public interface LinkServiceContract {

    /**
     * Creates a list of links.
     *
     * @param linkEntities the list of {@link LinkEntity} objects to be created.
     * @return the list of created {@link LinkEntity} objects.
     * @see LinkEntity
     */
    List<LinkEntity> create(List<LinkEntity> linkEntities);

    /**
     * Updates an existing link.
     *
     * @param linkVO the {@link LinkVO} object containing the updated link details.
     * @return the updated {@link LinkVO} object.
     * @see LinkVO
     */
    LinkVO update(LinkVO linkVO);

    /**
     * Finds a link by its ID.
     *
     * @param  id the ID of the link to find.
     * @return the {@link LinkVO} object with the specified ID.
     * @see LinkVO
     */
    LinkVO findLinkById(String id);

    /**
     * Deletes a link by its ID.
     *
     * @param id the ID of the link to delete.
     */
    @Transactional
    void delete(String id);
}
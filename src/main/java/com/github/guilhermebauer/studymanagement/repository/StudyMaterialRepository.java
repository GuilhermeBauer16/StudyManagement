package com.github.guilhermebauer.studymanagement.repository;

import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyMaterialRepository extends JpaRepository<StudyMaterialEntity,String> {

    @Query("SELECT l FROM StudyMaterialEntity sm JOIN sm.links l WHERE sm.id = :studyMaterialId")
    Page<LinkEntity> findLinksByStudyMaterialId(@Param("studyMaterialId") String studyMaterialId, Pageable pageable);

}

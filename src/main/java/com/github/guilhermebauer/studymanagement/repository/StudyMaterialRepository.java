package com.github.guilhermebauer.studymanagement.repository;

import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudyMaterialRepository extends JpaRepository<StudyMaterialEntity, String> {

    @Query("SELECT l FROM StudyMaterialEntity sm JOIN sm.links l WHERE sm.id = :studyMaterialId")
    Page<LinkEntity> findLinksByStudyMaterialId(@Param("studyMaterialId") String studyMaterialId, Pageable pageable);

    @Query("SELECT sm FROM StudyMaterialEntity sm WHERE sm.id = :studyMaterialId AND sm.userEntity.email = :userEmail")
    Optional<StudyMaterialEntity> findByIdAndUserEmail(@Param("studyMaterialId") String studyMaterialId, @Param("userEmail") String userEmail);

    @Query("SELECT sm FROM StudyMaterialEntity sm WHERE sm.userEntity.email = :userEmail")
    Page<StudyMaterialEntity> findAllByUserEmail(@Param("userEmail") String userEmail, Pageable pageable);

}

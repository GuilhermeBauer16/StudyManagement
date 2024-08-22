package com.github.guilhermebauer.studymanagement.repository;

import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMaterialRepository extends JpaRepository<StudyMaterialEntity,String> {
}

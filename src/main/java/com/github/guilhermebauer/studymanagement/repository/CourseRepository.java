package com.github.guilhermebauer.studymanagement.repository;

import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, String> {

    @Query("SELECT c FROM CourseEntity c WHERE c.title LIKE LOWER(CONCAT ('%',:title,'%')) AND c.userEntity.email = :userEmail")
    Page<CourseEntity> findByTitleAndEmail(@Param("title") String title, Pageable pageable, @Param("userEmail") String userEmail);

    @Query("SELECT c FROM CourseEntity c WHERE c.id = :courseId AND c.userEntity.email = :userEmail")
    Optional<CourseEntity> findByIdAndUserEmail(@Param("courseId")String courseId, @Param("userEmail") String userEmail);

    @Query("SELECT c FROM CourseEntity c WHERE c.userEntity.email = :userEmail")
    Page<CourseEntity> findAllByUserEmail(@Param("userEmail") String userEmail, Pageable pageable);

}

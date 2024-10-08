package com.github.guilhermebauer.studymanagement.repository;

import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.testContainers.AbstractionIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTest extends AbstractionIntegrationTest {

    @Autowired
    private CourseRepository repository;

    private CourseEntity savedCourseEntity;

    private static final String ID = "5f68880e-7356-4c86-a4a9-f8cc16e2ec87";
    private final String TITLE = "Math";
    private final String DESCRIPTION = "Math is a discipline that work with numbers";

    @BeforeEach
    void setUp(){
        savedCourseEntity = repository.save(new CourseEntity(ID, TITLE, DESCRIPTION));
    }

    @Test
    void testFindByCourse_WhenIsSuccessful_ShouldReturnPageableOfTheCourses(){

        Pageable pageable = Pageable.ofSize(10);
        Page<CourseEntity> courseByTitle = repository.findByTitle(savedCourseEntity.getTitle(), pageable);
        CourseEntity courseEntity = courseByTitle.getContent().getFirst();
        Assertions.assertNotNull(courseEntity);
        Assertions.assertEquals(ID, courseEntity.getId());
        Assertions.assertEquals(TITLE, courseEntity.getTitle());
        Assertions.assertEquals(DESCRIPTION, courseEntity.getDescription());
    }

}

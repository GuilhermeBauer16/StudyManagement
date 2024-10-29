package com.github.guilhermebauer.studymanagement.repository;

import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.UserEntity;
import com.github.guilhermebauer.studymanagement.testContainers.AbstractionIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.Set;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTest extends AbstractionIntegrationTest {

    private final CourseRepository repository;

    private final UserRepository userRepository;


    private CourseEntity savedCourseEntity;

    private static final String ID = "5f68880e-7356-4c86-a4a9-f8cc16e2ec87";
    private final String TITLE = "Math";
    private final String DESCRIPTION = "Math is a discipline that work with numbers";

    private static final String USER_NAME = "Paul";
    private static final String EMAIL = "Paultest@gmail.com";
    private static final String PASSWORD = "123456";
    private static final String USER_ROLE = "ROLE_USER";

    @Autowired
    CourseRepositoryTest(CourseRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;

    }

    @BeforeEach
    void setUp(){

        RoleEntity roleEntity = new RoleEntity(ID, USER_ROLE);

        UserEntity userEntity = new UserEntity(ID,USER_NAME,EMAIL,PASSWORD,  new HashSet<>(Set.of(roleEntity)));
        UserEntity savedUserEntity = userRepository.save(userEntity);
        savedCourseEntity = repository.save(new CourseEntity(ID, TITLE, DESCRIPTION, savedUserEntity));

    }

    @Test
    void testFindByTitleAndEmail_WhenIsSuccessful_ShouldReturnPageableOfTheCourses(){

        Pageable pageable = Pageable.ofSize(10);
        Page<CourseEntity> courseByTitle = repository.findByTitleAndEmail
                (savedCourseEntity.getTitle(), pageable, savedCourseEntity.getUserEntity().getEmail());
        CourseEntity courseEntity = courseByTitle.getContent().getFirst();
        Assertions.assertNotNull(courseEntity);
        Assertions.assertEquals(ID, courseEntity.getId());
        Assertions.assertEquals(TITLE, courseEntity.getTitle());
        Assertions.assertEquals(DESCRIPTION, courseEntity.getDescription());
    }

    @Test
    void testFindAllByEmail_WhenIsSuccessful_ShouldReturnPageableOfTheCourses(){

        Pageable pageable = Pageable.ofSize(10);
        Page<CourseEntity> allCourseByEmail = repository.findAllByUserEmail(EMAIL, pageable);

        CourseEntity courseEntity = allCourseByEmail.getContent().getFirst();
        Assertions.assertNotNull(courseEntity);
        Assertions.assertEquals(ID, courseEntity.getId());
        Assertions.assertEquals(TITLE, courseEntity.getTitle());
        Assertions.assertEquals(DESCRIPTION, courseEntity.getDescription());
    }

    @Test
    void testFindIdAndEmail_WhenIsSuccessful_ShouldReturnOptionalOfCourseEntity(){


        CourseEntity recoveredCourseEntity = repository.findByIdAndUserEmail(ID, EMAIL).orElse(null);

        Assertions.assertNotNull(recoveredCourseEntity);
        Assertions.assertEquals(ID, recoveredCourseEntity.getId());
        Assertions.assertEquals(TITLE, recoveredCourseEntity.getTitle());
        Assertions.assertEquals(DESCRIPTION, recoveredCourseEntity.getDescription());
    }


}

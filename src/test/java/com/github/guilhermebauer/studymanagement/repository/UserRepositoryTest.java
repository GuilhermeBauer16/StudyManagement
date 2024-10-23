package com.github.guilhermebauer.studymanagement.repository;

import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.UserEntity;
import com.github.guilhermebauer.studymanagement.testContainers.AbstractionIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends AbstractionIntegrationTest {

    @Autowired
    private UserRepository repository;


    UserEntity userEntity;


    private static final String ID = "d8e7df81-2cd4-41a2-a005-62e6d8079716";
    private static final String USER_NAME = "Paul";
    private static final String EMAIL = "Paultest@gmail.com";
    private static final String PASSWORD = "123456";
    private static final String USER_ROLE = "ROLE_USER";


    @BeforeEach
    void setUp() {

        RoleEntity roleEntity = new RoleEntity(ID, USER_ROLE);

        userEntity = new UserEntity(ID, USER_NAME, EMAIL, PASSWORD, new HashSet<>(Set.of(roleEntity)));
        userEntity = repository.save(userEntity);
    }

    @Test
    void testFindUserByEmail_WhenIsSuccessful_ThenReturnUserEntityObject() {

        UserEntity userByEmail = repository.findUserByEmail(userEntity.getEmail()).get();

        assertNotNull(userByEmail);
        assertEquals(ID, userByEmail.getId());
        assertEquals(USER_NAME, userByEmail.getName());
        assertEquals(EMAIL, userByEmail.getEmail());
        assertEquals(PASSWORD, userByEmail.getPassword());
        assertEquals(1,userByEmail.getRoles().size());


    }

}
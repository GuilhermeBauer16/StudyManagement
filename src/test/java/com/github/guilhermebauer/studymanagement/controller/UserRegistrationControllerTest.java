package com.github.guilhermebauer.studymanagement.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guilhermebauer.studymanagement.StudymanagementApplication;
import com.github.guilhermebauer.studymanagement.config.TestConfigs;
import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.UserEntity;
import com.github.guilhermebauer.studymanagement.repository.RoleRepository;
import com.github.guilhermebauer.studymanagement.repository.UserRepository;
import com.github.guilhermebauer.studymanagement.request.LoginRequest;
import com.github.guilhermebauer.studymanagement.response.LoginResponse;
import com.github.guilhermebauer.studymanagement.testContainers.AbstractionIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;

@SpringBootTest(classes = StudymanagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRegistrationControllerTest extends AbstractionIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static RoleEntity roleEntity;
    private static UserEntity userEntity;

    private static final String ID = "d8e7df81-2cd4-41a2-a005-62e6d8079716";
    private static final String USER_NAME = "John Doe";
    private static final String EMAIL = "john@example.com";
    private static final String PASSWORD = "password123";
    private static final String USER_ROLE = "ROLE_USER";

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        roleEntity = roleRepository.save(new RoleEntity(ID, USER_ROLE));
        userEntity = new UserEntity(ID, USER_NAME, EMAIL, passwordEncoder.encode(PASSWORD), new HashSet<>(Set.of(roleEntity)));
        userEntity = userRepository.save(userEntity);

        Assertions.assertNotNull(userEntity.getId(), "User should be created with an ID");
    }

    @Test
    @Order(1)
    void authorization() {
        LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);
        try {
            var accessToken = given()
                    .basePath("/api/login")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(loginRequest)
                    .filter(new RequestLoggingFilter(LogDetail.ALL))
                    .filter(new ResponseLoggingFilter(LogDetail.ALL))
                    .when()
                    .post()
                    .then()
                    .statusCode(200)
                    .extract()
                    .body().as(LoginResponse.class).getToken();

            specification = new RequestSpecBuilder()
                    .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                    .setBaseUri("http://localhost:" + TestConfigs.SERVER_PORT)
                    .setBasePath("/api/user")
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                    .build();
        } catch (BadCredentialsException e) {
            Assertions.fail("Login failed for user: " + EMAIL + " - " + e.getMessage());
        }
    }
}


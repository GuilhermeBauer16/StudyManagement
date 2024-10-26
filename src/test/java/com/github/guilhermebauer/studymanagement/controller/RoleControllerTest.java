package com.github.guilhermebauer.studymanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guilhermebauer.studymanagement.StudymanagementApplication;
import com.github.guilhermebauer.studymanagement.config.TestConfigs;
import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.UserEntity;
import com.github.guilhermebauer.studymanagement.model.values.RoleVO;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = StudymanagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class RoleControllerTest extends AbstractionIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static RoleVO roleVO;


    private static final String ID = "d8e7df81-2cd4-41a2-a005-62e6d8079716";
    private static final String ROLE_NAME = "TEST";
    private static final String USER_NAME = "john";
    private static final String EMAIL = "jonhBee@gmail.com";
    private static final String PASSWORD = "123456";
    private static final String USER_ROLE = "ROLE_ADMIN";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ROLE_PREFIX = "ROLE_";
    private static final Set<RoleEntity> ROLES = new HashSet<>(Set.of(new RoleEntity(ID, USER_ROLE)));

    @BeforeAll
    public static void setup(
            @Autowired UserRepository userRepository,
            @Autowired PasswordEncoder passwordEncoder) {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        UserEntity userEntity = new UserEntity(ID, USER_NAME, EMAIL, passwordEncoder.encode(PASSWORD), ROLES);
        userEntity = userRepository.save(userEntity);
        roleVO = new RoleVO(ID, ROLE_NAME);

    }

    @Test
    @Order(1)
    void authorization() {
        LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);

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
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, BEARER_PREFIX + accessToken)
                .setBaseUri("http://localhost:" + TestConfigs.SERVER_PORT)
                .setBasePath("/api/role")
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

    }

    @Test
    @Order(2)
    void testCreateRole_whenSuccess_ShouldReturnARoleObject() throws IOException {

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(roleVO)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();


        RoleVO roleValue = objectMapper.readValue(content, RoleVO.class);

        Assertions.assertNotNull(roleValue);
        Assertions.assertNotNull(roleValue.getId());
        Assertions.assertTrue(roleValue.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));

        assertEquals(ROLE_PREFIX + ROLE_NAME, roleValue.getName());


        roleVO.setId(roleValue.getId());

    }

    @Test
    @Order(3)
    void givenStudyMaterialObject_when_FindStudyMaterialById_ShouldReturnAStudyMaterialObject() throws JsonProcessingException {

        var content = given().spec(specification)
                .pathParam("name", roleVO.getName())
                .when()
                .get("/findByRoleName/{name}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        RoleVO roleValue = objectMapper.readValue(content, RoleVO.class);

        Assertions.assertNotNull(roleValue);
        Assertions.assertNotNull(roleValue.getId());
        Assertions.assertTrue(roleValue.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));

        assertEquals(ROLE_PREFIX + ROLE_NAME, roleValue.getName());


    }
}



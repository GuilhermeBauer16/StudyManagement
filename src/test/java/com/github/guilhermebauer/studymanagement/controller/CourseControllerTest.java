package com.github.guilhermebauer.studymanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guilhermebauer.studymanagement.StudymanagementApplication;
import com.github.guilhermebauer.studymanagement.config.TestConfigs;
import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.UserEntity;
import com.github.guilhermebauer.studymanagement.model.values.CourseVO;
import com.github.guilhermebauer.studymanagement.repository.RoleRepository;
import com.github.guilhermebauer.studymanagement.repository.UserRepository;
import com.github.guilhermebauer.studymanagement.request.LoginRequest;
import com.github.guilhermebauer.studymanagement.response.CourseResponse;
import com.github.guilhermebauer.studymanagement.response.LoginResponse;
import com.github.guilhermebauer.studymanagement.testContainers.AbstractionIntegrationTest;
import com.github.guilhermebauer.studymanagement.utils.PaginatedResponse;
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
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = StudymanagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
class CourseControllerTest extends AbstractionIntegrationTest {


    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static CourseVO courseVO;

    private static final String ID = "cb1a4d81-27d3-443f-be22-e17c52c83ed8";
    private static final String TITLE = "Math";
    private static final String DESCRIPTION = "Math is a discipline that work with numbers";
    private static final String UPDATED_DESCRIPTION = "Physical is a discipline that work with numbers";
    private static final String UPDATED_TITLE = "Physical";

    private static final String USER_NAME = "john";
    private static final String EMAIL = "jonhDoe@gmail.com";
    private static final String PASSWORD = "123456";
    private static final String USER_ROLE = "ROLE_USER";
    private static final String BEARER_PREFIX = "Bearer ";


    @BeforeAll
    static void SetUp(@Autowired RoleRepository roleRepository, @Autowired UserRepository userRepository, @Autowired PasswordEncoder passwordEncoder) {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        courseVO = new CourseVO(ID, TITLE, DESCRIPTION);
        RoleEntity roleEntity = roleRepository.save(new RoleEntity(ID, USER_ROLE));
        UserEntity userEntity = new UserEntity(ID, USER_NAME, EMAIL, passwordEncoder.encode(PASSWORD), new HashSet<>(Set.of(roleEntity)));
        userEntity = userRepository.save(userEntity);
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
                .setBasePath("/api/course")
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

    }


    @Test
    @Order(2)
    void givenCourseObject_when_CreateCourse_ShouldReturnACourseObject() throws IOException {

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(courseVO)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();


        CourseResponse courseResponse = objectMapper.readValue(content, CourseResponse.class);

        Assertions.assertNotNull(courseResponse);
        Assertions.assertNotNull(courseResponse.getId());
        Assertions.assertTrue(courseResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));

        assertEquals(TITLE, courseResponse.getTitle());
        assertEquals(DESCRIPTION, courseResponse.getDescription());

        courseVO.setId(courseResponse.getId());

    }

    @Test
    @Order(3)
    void givenCourseObject_when_FindCourseById_ShouldReturnACourseObject() throws JsonProcessingException {


        var content = given().spec(specification)
                .pathParam("id", courseVO.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        CourseResponse courseResponse = objectMapper.readValue(content, CourseResponse.class);

        Assertions.assertNotNull(courseResponse);
        Assertions.assertNotNull(courseResponse.getId());
        Assertions.assertTrue(courseResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));

        assertEquals(TITLE, courseResponse.getTitle());
        assertEquals(DESCRIPTION, courseResponse.getDescription());


    }

    @Test
    @Order(4)
    void givenCourseList_when_FindAllCourses_ShouldReturnACourseList() throws JsonProcessingException {

        var content = given().spec(specification)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PaginatedResponse<CourseResponse> paginatedResponse =
                objectMapper.readValue(content, new TypeReference<>() {
                });

        List<CourseResponse> courseResponseList = paginatedResponse.getContent();
        CourseResponse courseResponse = courseResponseList.getFirst();

        Assertions.assertNotNull(courseResponse);
        Assertions.assertNotNull(courseResponse.getId());
        Assertions.assertTrue(courseResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));

        assertEquals(TITLE, courseResponse.getTitle());
        assertEquals(DESCRIPTION, courseResponse.getDescription());


    }

    @Test
    @Order(5)
    void givenFindByTitle_whenCourseWasFound_ShouldReturnAPageableOfCourse() throws JsonProcessingException {

        var content = given().spec(specification)
                .when()
                .pathParam("title", courseVO.getTitle())
                .get("/findByTitle/{title}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PaginatedResponse<CourseResponse> paginatedResponse =
                objectMapper.readValue(content, new TypeReference<>() {
                });

        List<CourseResponse> courseResponseList = paginatedResponse.getContent();
        CourseResponse courseResponse = courseResponseList.getFirst();

        Assertions.assertNotNull(courseResponse);
        Assertions.assertNotNull(courseResponse.getId());
        Assertions.assertTrue(courseResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));

        assertEquals(TITLE, courseResponse.getTitle());
        assertEquals(DESCRIPTION, courseResponse.getDescription());


    }


    @Test
    @Order(6)
    void GivenCourseObject_when_UpdateCourse_ShouldReturnACourseObject() throws IOException {

        courseVO.setTitle(UPDATED_TITLE);
        courseVO.setDescription(UPDATED_DESCRIPTION);

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(courseVO)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        CourseResponse courseResponse = objectMapper.readValue(content, CourseResponse.class);

        Assertions.assertNotNull(courseResponse);
        Assertions.assertNotNull(courseResponse.getId());
        Assertions.assertTrue(courseResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));

        assertEquals(UPDATED_TITLE, courseResponse.getTitle());
        assertEquals(UPDATED_DESCRIPTION, courseResponse.getDescription());
    }

    @Order(7)
    @Test
    void givenPersonalizedWorkoutExercise_when_delete_ShouldReturnNoContent() {

        given().spec(specification)
                .pathParam("id", courseVO.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);

    }


}

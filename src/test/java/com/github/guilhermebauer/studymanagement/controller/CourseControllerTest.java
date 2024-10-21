//package com.github.guilhermebauer.studymanagement.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.guilhermebauer.studymanagement.StudymanagementApplication;
//import com.github.guilhermebauer.studymanagement.config.TestConfigs;
//import com.github.guilhermebauer.studymanagement.enums.UserProfile;
//import com.github.guilhermebauer.studymanagement.model.RoleEntity;
//import com.github.guilhermebauer.studymanagement.model.values.CourseVO;
//import com.github.guilhermebauer.studymanagement.model.values.UserVO;
//import com.github.guilhermebauer.studymanagement.request.LoginRequest;
//import com.github.guilhermebauer.studymanagement.response.CourseResponse;
//import com.github.guilhermebauer.studymanagement.response.LoginResponse;
//import com.github.guilhermebauer.studymanagement.response.UserRegistrationResponse;
//import com.github.guilhermebauer.studymanagement.testContainers.AbstractionIntegrationTest;
//import com.github.guilhermebauer.studymanagement.utils.PaginatedResponse;
//import io.restassured.builder.RequestSpecBuilder;
//import io.restassured.filter.log.LogDetail;
//import io.restassured.filter.log.RequestLoggingFilter;
//import io.restassured.filter.log.ResponseLoggingFilter;
//import io.restassured.specification.RequestSpecification;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.config.EnableSpringDataWebSupport;
//import org.testcontainers.shaded.com.google.common.collect.Sets;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//
//import static io.restassured.RestAssured.given;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@SpringBootTest(classes = StudymanagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
//class CourseControllerTest extends AbstractionIntegrationTest {
//
//
//    private static RequestSpecification specification;
//    private static ObjectMapper objectMapper;
//    private static CourseVO courseVO;
//
//    private static final String ID = "d8e7df81-2cd4-41a2-a005-62e6d8079716";
//    private static final String TITLE = "Math";
//    private static final String DESCRIPTION = "Math is a discipline that work with numbers";
//
//    private static final String USER_NAME = "john";
//    private static final String EMAIL = "jonhBee@gmail.com";
//    private static final String PASSWORD = "123456";
//    private static final String USER_ROLE = "ROLE_USER";
//
//    private static final Set<RoleEntity> USER_ROLES = Sets.newHashSet(new RoleEntity(ID, USER_ROLE));
//
//    @BeforeAll
//    static void SetUp() {
//        objectMapper = new ObjectMapper();
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//
//        courseVO = new CourseVO(ID, TITLE, DESCRIPTION);
//    }
//
//
//
//    @Test
//    @Order(1)
//    void signUp() {
//
//        Set<RoleEntity> USER_ROLES = Sets.newHashSet(new RoleEntity(ID, USER_ROLE));
//
//        UserVO userVO = new UserVO(ID, USER_NAME, EMAIL, PASSWORD, USER_ROLES);
//
//        given()
//                .basePath("/api/signUp")
//                .port(8889)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .body(userVO)
//                .when()
//                .post()
//                .then()
//                .statusCode(201)
//                .extract()
//                .body()
//                .asString();
//
//
//    }
//
//    @Test
//    @Order(2)
//    void authorization() {
//
//        LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);
//        var accessToken = given()
//                .basePath("/api/login")
//                .port(TestConfigs.SERVER_PORT)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .body(loginRequest)
//                .when()
//                .post()
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .as(LoginResponse.class).getToken();
//
//
//        specification = new RequestSpecBuilder()
//                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
//                .setBasePath("/api/course")
//                .setPort(TestConfigs.SERVER_PORT)
//                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
//                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
//                .build();
//    }
//
//
//
//
//    @Test
//    @Order(3)
//    void givenCourseObject_when_CreateCourse_ShouldReturnACourseObject() throws IOException {
//
//        var content = given().spec(specification)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .body(courseVO)
//                .when()
//                .post()
//                .then()
//                .statusCode(201)
//                .extract()
//                .body()
//                .asString();
//
//
//        CourseResponse courseResponse = objectMapper.readValue(content, CourseResponse.class);
//
//        Assertions.assertNotNull(courseResponse);
//        Assertions.assertNotNull(courseResponse.getId());
//        Assertions.assertTrue(courseResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals(TITLE, courseResponse.getTitle());
//        assertEquals(DESCRIPTION, courseResponse.getDescription());
//
//        courseVO.setId(courseResponse.getId());
//
//    }
//
//    @Test
//    @Order(4)
//    void givenCourseObject_when_FindCourseById_ShouldReturnACourseObject() throws JsonProcessingException {
//
//        var content = given().spec(specification)
//                .pathParam("id", courseVO.getId())
//                .when()
//                .get("/{id}")
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .asString();
//
//        CourseResponse courseResponse = objectMapper.readValue(content, CourseResponse.class);
//
//        Assertions.assertNotNull(courseResponse);
//        Assertions.assertNotNull(courseResponse.getId());
//        Assertions.assertTrue(courseResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals(TITLE, courseResponse.getTitle());
//        assertEquals(DESCRIPTION, courseResponse.getDescription());
//
//
//    }
//
//    @Test
//    @Order(5)
//    void givenCourseList_when_FindAllCourses_ShouldReturnACourseList() throws JsonProcessingException {
//
//        var content = given().spec(specification)
//                .when()
//                .get()
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .asString();
//
//        PaginatedResponse<CourseResponse> paginatedResponse =
//                objectMapper.readValue(content, new TypeReference<>() {
//                });
//
//        List<CourseResponse> courseResponseList = paginatedResponse.getContent();
//        CourseResponse courseResponse = courseResponseList.getFirst();
//
//        Assertions.assertNotNull(courseResponse);
//        Assertions.assertNotNull(courseResponse.getId());
//        Assertions.assertTrue(courseResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals(TITLE, courseResponse.getTitle());
//        assertEquals(DESCRIPTION, courseResponse.getDescription());
//
//
//    }
//
//    @Test
//    @Order(6)
//    void givenFindByTitle_whenCourseWasFound_ShouldReturnAPageableOfCourse() throws JsonProcessingException {
//
//        var content = given().spec(specification)
//                .when()
//                .pathParam("title", courseVO.getTitle())
//                .get("/findByTitle/{title}")
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .asString();
//
//        PaginatedResponse<CourseResponse> paginatedResponse =
//                objectMapper.readValue(content, new TypeReference<>() {
//                });
//
//        List<CourseResponse> courseResponseList = paginatedResponse.getContent();
//        CourseResponse courseResponse = courseResponseList.getFirst();
//
//        Assertions.assertNotNull(courseResponse);
//        Assertions.assertNotNull(courseResponse.getId());
//        Assertions.assertTrue(courseResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals(TITLE, courseResponse.getTitle());
//        assertEquals(DESCRIPTION, courseResponse.getDescription());
//
//
//    }
//
//
//    @Test
//    @Order(7)
//    void GivenCourseObject_when_UpdateCourse_ShouldReturnACourseObject() throws IOException {
//
//        courseVO.setTitle("Physical");
//        courseVO.setDescription("Physical is a discipline that work with numbers");
//
//        var content = given().spec(specification)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .body(courseVO)
//                .when()
//                .put()
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .asString();
//
//        CourseResponse courseResponse = objectMapper.readValue(content, CourseResponse.class);
//
//        Assertions.assertNotNull(courseResponse);
//        Assertions.assertNotNull(courseResponse.getId());
//        Assertions.assertTrue(courseResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals("Physical", courseResponse.getTitle());
//        assertEquals("Physical is a discipline that work with numbers", courseResponse.getDescription());
//    }
//
//    @Order(8)
//    @Test
//    void givenPersonalizedWorkoutExercise_when_delete_ShouldReturnNoContent() {
//
//        given().spec(specification)
//                .pathParam("id", courseVO.getId())
//                .when()
//                .delete("{id}")
//                .then()
//                .statusCode(204);
//
//    }
//
//
//}

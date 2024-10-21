//package com.github.guilhermebauer.studymanagement.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.guilhermebauer.studymanagement.StudymanagementApplication;
//import com.github.guilhermebauer.studymanagement.config.TestConfigs;
//import com.github.guilhermebauer.studymanagement.model.CourseEntity;
//import com.github.guilhermebauer.studymanagement.model.LinkEntity;
//import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
//import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
//import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
//import com.github.guilhermebauer.studymanagement.repository.CourseRepository;
//import com.github.guilhermebauer.studymanagement.repository.LinkRepository;
//import com.github.guilhermebauer.studymanagement.repository.StudyMaterialRepository;
//import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
//import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
//import com.github.guilhermebauer.studymanagement.request.StudyMaterialUpdateRequest;
//import com.github.guilhermebauer.studymanagement.response.StudyMaterialUpdateResponse;
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
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.config.EnableSpringDataWebSupport;
//
//import java.io.IOException;
//import java.util.List;
//
//import static io.restassured.RestAssured.given;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@SpringBootTest(classes = StudymanagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
//class StudyMaterialControllerTest extends AbstractionIntegrationTest {
//
//
//    private static RequestSpecification specification;
//    private static ObjectMapper objectMapper;
//    private static StudyMaterialVO studyMaterialVO;
//    private static LinkListToStudyMaterialRequest linkListToStudyMaterialRequest;
//    private static SingleLinkToStudyMaterialRequest singleLinkToStudyMaterialRequest;
//    private static StudyMaterialUpdateRequest studyMaterialUpdateRequest;
//
//    private static final String ID = "5f68880e-7356-4c86-a4a9-f8cc16e2ec87";
//    private static final String URL = "https://start.spring.io/";
//    private static final String DESCRIPTION = "Spring Boot Tutorial";
//    private static final String TITLE = "Introduction to Spring Boot";
//    private static final String CONTENT = "This is a comprehensive guide to Spring Boot.";
//    private static final String COURSE_TITLE = "Math";
//    private static final String COURSE_DESCRIPTION = "Math is a discipline that work with numbers";
//
//    @BeforeAll
//    static void SetUp(@Autowired CourseRepository courseRepository,
//                      @Autowired LinkRepository linkRepository,
//                      @Autowired StudyMaterialRepository studyMaterialRepository) {
//        objectMapper = new ObjectMapper();
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//
//        specification = new RequestSpecBuilder()
//                .setBasePath("/api/studyMaterial")
//                .setPort(TestConfigs.SERVER_PORT)
//                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
//                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
//                .build();
//
//
//        LinkEntity linkEntity = new LinkEntity(ID, URL, DESCRIPTION);
//        linkRepository.save(linkEntity);
//
//        LinkVO linkVO = new LinkVO(ID, URL, DESCRIPTION);
//
//        CourseEntity courseEntity = new CourseEntity(ID, COURSE_TITLE, COURSE_DESCRIPTION);
//        courseRepository.save(courseEntity);
//
//        studyMaterialVO = new StudyMaterialVO(ID, TITLE, CONTENT, courseEntity, List.of(linkEntity));
//        StudyMaterialEntity studyMaterialEntity
//                = new StudyMaterialEntity(ID, TITLE, CONTENT, courseEntity, List.of(linkEntity));
//        singleLinkToStudyMaterialRequest = new SingleLinkToStudyMaterialRequest(ID, linkVO);
//        linkListToStudyMaterialRequest = new LinkListToStudyMaterialRequest(ID, List.of(linkEntity));
//        studyMaterialUpdateRequest = new StudyMaterialUpdateRequest(ID, TITLE, CONTENT);
//
//        studyMaterialRepository.save(studyMaterialEntity);
//
//
//    }
//
//
//    @Test
//    @Order(1)
//    void givenStudyMaterialObject_when_CreateStudyMaterial_ShouldReturnAStudyMaterialObject() throws IOException {
//
//        var content = given().spec(specification)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .body(studyMaterialVO)
//                .when()
//                .post()
//                .then()
//                .statusCode(201)
//                .extract()
//                .body()
//                .asString();
//
//
//        StudyMaterialVO studyMaterialVOResponse = objectMapper.readValue(content, StudyMaterialVO.class);
//
//        Assertions.assertNotNull(studyMaterialVOResponse);
//        Assertions.assertNotNull(studyMaterialVOResponse.getId());
//        assertTrue(studyMaterialVOResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals(CONTENT, studyMaterialVOResponse.getContent());
//        assertEquals(TITLE, studyMaterialVOResponse.getTitle());
//        assertEquals(COURSE_TITLE, studyMaterialVOResponse.getCourseEntity().getTitle());
//        assertEquals(COURSE_DESCRIPTION, studyMaterialVOResponse.getCourseEntity().getDescription());
//        assertEquals(URL, studyMaterialVOResponse.getLinks().getFirst().getUrl());
//        assertEquals(DESCRIPTION, studyMaterialVOResponse.getLinks().getFirst().getDescription());
//
//
//
//        studyMaterialVO.setId(studyMaterialVOResponse.getId());
//
//    }
//
//    @Test
//    @Order(2)
//    void givenStudyMaterialObject_when_FindStudyMaterialById_ShouldReturnAStudyMaterialObject() throws JsonProcessingException {
//
//        var content = given().spec(specification)
//                .pathParam("id", studyMaterialVO.getId())
//                .when()
//                .get("/{id}")
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .asString();
//
//        StudyMaterialVO studyMaterialVOResponse = objectMapper.readValue(content, StudyMaterialVO.class);
//
//        Assertions.assertNotNull(studyMaterialVOResponse);
//        Assertions.assertNotNull(studyMaterialVOResponse.getId());
//        assertTrue(studyMaterialVOResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals(CONTENT, studyMaterialVOResponse.getContent());
//        assertEquals(TITLE, studyMaterialVOResponse.getTitle());
//        assertEquals(COURSE_TITLE, studyMaterialVOResponse.getCourseEntity().getTitle());
//        assertEquals(COURSE_DESCRIPTION, studyMaterialVOResponse.getCourseEntity().getDescription());
//        assertEquals(URL, studyMaterialVOResponse.getLinks().getFirst().getUrl());
//        assertEquals(DESCRIPTION, studyMaterialVOResponse.getLinks().getFirst().getDescription());
//
//
//
//    }
//
//    @Test
//    @Order(3)
//    void givenStudyMaterialList_when_FindAllStudyMaterial_ShouldReturnAStudyMaterialList() throws JsonProcessingException {
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
//        PaginatedResponse<StudyMaterialVO> paginatedResponse =
//                objectMapper.readValue(content, new TypeReference<>() {
//                });
//
//        List<StudyMaterialVO> studyMaterialVOList = paginatedResponse.getContent();
//        StudyMaterialVO studyMaterialVOResponse = studyMaterialVOList.getFirst();
//
//        Assertions.assertNotNull(studyMaterialVOResponse);
//        Assertions.assertNotNull(studyMaterialVOResponse.getId());
//        assertTrue(studyMaterialVOResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals(CONTENT, studyMaterialVOResponse.getContent());
//        assertEquals(TITLE, studyMaterialVOResponse.getTitle());
//        assertEquals(COURSE_TITLE, studyMaterialVOResponse.getCourseEntity().getTitle());
//        assertEquals(COURSE_DESCRIPTION, studyMaterialVOResponse.getCourseEntity().getDescription());
//        assertEquals(URL, studyMaterialVOResponse.getLinks().getFirst().getUrl());
//        assertEquals(DESCRIPTION, studyMaterialVOResponse.getLinks().getFirst().getDescription());
//
//
//
//    }
//
//
//    @Test
//    @Order(4)
//    void givenStudyMaterialObject_when_AddLink_ShouldReturnAStudyMaterialObject() throws JsonProcessingException {
//
//        var content = given().spec(specification)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .body(linkListToStudyMaterialRequest)
//                .when()
//                .post("/addLinks")
//                .then()
//                .log().all() // Log the response for debugging purposes
//                .statusCode(201)
//                .extract()
//                .body()
//                .asString();
//
//
//        StudyMaterialVO studyMaterialVOResponse = objectMapper.readValue(content, StudyMaterialVO.class);
//
//        Assertions.assertNotNull(studyMaterialVOResponse);
//        Assertions.assertNotNull(studyMaterialVOResponse.getId());
//        assertTrue(studyMaterialVOResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals(CONTENT, studyMaterialVOResponse.getContent());
//        assertEquals(TITLE, studyMaterialVOResponse.getTitle());
//        assertEquals(COURSE_TITLE, studyMaterialVOResponse.getCourseEntity().getTitle());
//        assertEquals(COURSE_DESCRIPTION, studyMaterialVOResponse.getCourseEntity().getDescription());
//        assertEquals(URL, studyMaterialVOResponse.getLinks().getFirst().getUrl());
//        assertEquals(DESCRIPTION, studyMaterialVOResponse.getLinks().getFirst().getDescription());
//
//    }
//
//    @Test
//    @Order(5)
//    void givenStudyMaterialObject_when_FindAllLinks_ShouldReturnALinkList() throws JsonProcessingException {
//
//        var content = given().spec(specification)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .pathParam("id", studyMaterialVO.getId())
//                .when()
//                .get("/findAllLinks/{id}")
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .asString();
//
//
//        PaginatedResponse<LinkVO> paginatedResponse =
//                objectMapper.readValue(content, new TypeReference<>() {
//                });
//
//        List<LinkVO> linkVOS = paginatedResponse.getContent();
//        LinkVO linkVOResponse = linkVOS.getFirst();
//
//        Assertions.assertNotNull(linkVOResponse);
//        Assertions.assertNotNull(linkVOResponse.getId());
//        assertTrue(linkVOResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals(DESCRIPTION, linkVOResponse.getDescription());
//        assertEquals(URL, linkVOResponse.getUrl());
//
//    }
//
//    @Test
//    @Order(6)
//    void givenStudyMaterialObject_when_UpdateALink_ShouldReturnAStudyMaterialObject() throws JsonProcessingException {
//
//        var content = given().spec(specification)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .body(singleLinkToStudyMaterialRequest)
//                .when()
//                .put("/updateLink")
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .asString();
//
//
//        StudyMaterialVO studyMaterialVOResponse = objectMapper.readValue(content, StudyMaterialVO.class);
//
//        Assertions.assertNotNull(studyMaterialVOResponse);
//        Assertions.assertNotNull(studyMaterialVOResponse.getId());
//        assertTrue(studyMaterialVOResponse.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals(CONTENT, studyMaterialVOResponse.getContent());
//        assertEquals(TITLE, studyMaterialVOResponse.getTitle());
//        assertEquals(COURSE_TITLE, studyMaterialVOResponse.getCourseEntity().getTitle());
//        assertEquals(COURSE_DESCRIPTION, studyMaterialVOResponse.getCourseEntity().getDescription());
//        assertEquals(URL, studyMaterialVOResponse.getLinks().getFirst().getUrl());
//        assertEquals(DESCRIPTION, studyMaterialVOResponse.getLinks().getFirst().getDescription());
//
//    }
//
//
//    @Test
//    @Order(7)
//    void givenStudyMaterialUpdateRequest_when_UpdateAStudyMaterial_ShouldReturnUpdatedStudyMaterial() throws IOException {
//
//        studyMaterialUpdateRequest.setContent("A Spring Boot guide");
//        studyMaterialUpdateRequest.setTitle("Spring Boot introduction");
//
//        var content = given().spec(specification)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .body(studyMaterialUpdateRequest)
//                .when()
//                .put()
//                .then()
//                .statusCode(200)
//                .extract()
//                .body()
//                .asString();
//
//        StudyMaterialUpdateResponse response = objectMapper.readValue(content, StudyMaterialUpdateResponse.class);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertNotNull(response.getId());
//        assertTrue(response.getId().matches("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
//
//        assertEquals("A Spring Boot guide", response.getContent());
//        assertEquals("Spring Boot introduction", response.getTitle());
//
//
//    }
//
//    @Test
//    @Order(8)
//    void givenStudyMaterialObject_when_DeleteALink_ShouldReturnNoContent(){
//
//        given().spec(specification)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .body(singleLinkToStudyMaterialRequest)
//                .when()
//                .delete("/deleteLinks")
//                .then()
//                .statusCode(204)
//                .extract()
//                .body()
//                .asString();
//
//
//    }
//
//    @Order(9)
//    @Test
//    void givenPersonalizedWorkoutExercise_when_delete_ShouldReturnNoContent() {
//
//        given().spec(specification)
//                .pathParam("id", studyMaterialVO.getId())
//                .when()
//                .delete("{id}")
//                .then()
//                .statusCode(204);
//
//    }
//
//
//}

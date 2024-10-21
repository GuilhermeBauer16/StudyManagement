//package com.github.guilhermebauer.studymanagement.controller;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.guilhermebauer.studymanagement.StudymanagementApplication;
//import com.github.guilhermebauer.studymanagement.config.TestConfigs;
//import com.github.guilhermebauer.studymanagement.mapper.BuildMapper;
//import com.github.guilhermebauer.studymanagement.model.RoleEntity;
//import com.github.guilhermebauer.studymanagement.model.UserEntity;
//import com.github.guilhermebauer.studymanagement.model.values.UserVO;
//import com.github.guilhermebauer.studymanagement.repository.RoleRepository;
//import com.github.guilhermebauer.studymanagement.repository.UserRepository;
//import com.github.guilhermebauer.studymanagement.request.LoginRequest;
//import com.github.guilhermebauer.studymanagement.response.LoginResponse;
//import com.github.guilhermebauer.studymanagement.response.UserRegistrationResponse;
//import com.github.guilhermebauer.studymanagement.testContainers.AbstractionIntegrationTest;
//import io.restassured.builder.RequestSpecBuilder;
//import io.restassured.filter.log.LogDetail;
//import io.restassured.filter.log.RequestLoggingFilter;
//import io.restassured.filter.log.ResponseLoggingFilter;
//import io.restassured.response.ValidatableResponse;
//import io.restassured.specification.RequestSpecification;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.config.EnableSpringDataWebSupport;
//import org.testcontainers.shaded.com.google.common.collect.Sets;
//
//import java.util.UUID;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@SpringBootTest(classes = StudymanagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
//public class UserRegistrationControllerTest extends AbstractionIntegrationTest {
//
//
//    private static RequestSpecification specification;
//    private static ObjectMapper objectMapper;
//    private static UserRegistrationResponse userResponse;
//    private static RoleEntity roleEntity;
//    private static UserEntity userEntity;
//    UserVO userVO;
//
//    private static final String ID = "d8e7df81-2cd4-41a2-a005-62e6d8079716";
//    private static final String USER_NAME = "john";
//    private static final String EMAIL = "JohnWick@gmail.com";
//    private static final String PASSWORD = "123456";
//    private static final String USER_ROLE = "ROLE_USER";
//
//    @BeforeAll
//    public static void setup(@Autowired RoleRepository roleRepository,
//                             @Autowired UserRepository userRepository) {
//
//        objectMapper = new ObjectMapper();
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//
//
//
//                RoleEntity role = new RoleEntity();
//                role.setId(UUID.randomUUID().toString());
//                role.setName("ROLE_USER");
//                roleRepository.save(role);
//
//
//    }
//
//    @Test
//    @Order(1)
//    void givenUserVo_whenSignUp_ShouldReturnAUserResponseObject() throws JsonProcessingException {
//
//        String uniqueEmail = "JohnDoe" + System.currentTimeMillis() + "@gmail.com"; // Append timestamp to make email unique
//
//        UserVO userVO = new UserVO();
//        userVO.setId(UUID.randomUUID().toString());
//        userVO.setName("john");
//        userVO.setEmail(uniqueEmail); // Use unique email here
//        userVO.setPassword("123456");
//        userVO.setRoles(Sets.newHashSet(new RoleEntity(UUID.randomUUID().toString(), "ROLE_USER")));
//
//        ValidatableResponse validatableResponse = given()
//                .basePath("/api/signUp")
//                .port(TestConfigs.SERVER_PORT)
//                .contentType(TestConfigs.CONTENT_TYPE_JSON)
//                .body(userVO)
//                .filter(new RequestLoggingFilter(LogDetail.ALL))
//                .filter(new ResponseLoggingFilter(LogDetail.ALL))
//                .when()
//                .post()
//                .then()
//                .statusCode(201) // Expect a 201 Created status
//                .body("name", equalTo("john"))
//                .body("email", equalTo(uniqueEmail)); // Check the unique email is correctly returned
//    }
//
//    @Test
//    @Order(2)
//    void givenLoginRequest_whenLogIn_ShouldReturnAUserResponseObject() {
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
//                .as(LoginResponse.class)
//                .getToken();
//
//
//        specification = new RequestSpecBuilder()
//                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
//                .setBaseUri("http://localhost:" + TestConfigs.SERVER_PORT)
//                .setBasePath("/api/user")
//                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
//                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
//                .build();
//    }
//}

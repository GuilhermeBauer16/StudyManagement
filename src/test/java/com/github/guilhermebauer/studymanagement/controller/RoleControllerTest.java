package com.github.guilhermebauer.studymanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guilhermebauer.studymanagement.StudymanagementApplication;
import com.github.guilhermebauer.studymanagement.config.TestConfigs;
import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.values.RoleVO;
import com.github.guilhermebauer.studymanagement.response.UserRegistrationResponse;
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
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = StudymanagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoleControllerTest extends AbstractionIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static UserRegistrationResponse userResponse;
    private static RoleEntity roleEntity;
    private static RoleVO roleVO;



    private static final String ID = "d8e7df81-2cd4-41a2-a005-62e6d8079716";
    private static final String ROLE_NAME = "ROLE_ADMIN";

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        specification = new RequestSpecBuilder()
                .setBasePath("/api/role")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
        roleEntity = new RoleEntity(ID, ROLE_NAME);
        roleVO = new RoleVO(ID, ROLE_NAME);

    }

    @Test
    @Order(1)
    void authorization() throws JsonProcessingException {


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

        assertEquals(ROLE_NAME, roleValue.getName());

        roleValue.setId(roleValue.getId());

    }
}



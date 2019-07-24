package com.lambdaschool.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.repository.InstructorRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.number.OrderingComparison.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private InstructorRepository instructrepo;

    @Before
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    // GET /courses/courses
    @Test
    public void whenMeasuredResponseTime() {
        given().when().get("/courses/courses").then().time(lessThan(5000L));
    }

    // POST /courses/course/add
    @Test
    public void givenPostACourse() throws Exception {
        Instructor i1 = instructrepo.findById(2L).orElseThrow(() -> new EntityNotFoundException(Long.toString(2L)));
        Course c1 = new Course("Business", i1);
        c1.setCourseid(100);

        ObjectMapper mapper = new ObjectMapper();
        String stringC1 = mapper.writeValueAsString(c1);

        given().contentType("application/json").body(stringC1).when().post("/courses/course/add").then().statusCode(201);
    }
}

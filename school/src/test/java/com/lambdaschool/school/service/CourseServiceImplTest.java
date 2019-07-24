package com.lambdaschool.school.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.repository.InstructorRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolApplication.class)
public class CourseServiceImplTest {
    @Autowired
    private CourseService courseService;

    @Autowired
    private InstructorRepository instructrepo;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void findCourseById() {
        assertEquals("Data Science", courseService.findCourseById(1).getCoursename());
    }

    @Test
    public void deleteFound() {
        courseService.delete(1);
        assertEquals(5, courseService.findAll().size());
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteNotFound() {
        courseService.delete(100);
        assertEquals(6, courseService.findAll().size());
    }

    @Test
    public void save() {
        Instructor i1 = instructrepo.findById(2L).orElseThrow(() -> new EntityNotFoundException(Long.toString(2L)));
        Course c1 = new Course("Business", i1);

        Course addCourse = courseService.save(c1);

        assertNotNull(addCourse);

        Course foundCourse = courseService.findCourseById(addCourse.getCourseid());
        assertEquals(addCourse.getCoursename(), foundCourse.getCoursename());
    }
}

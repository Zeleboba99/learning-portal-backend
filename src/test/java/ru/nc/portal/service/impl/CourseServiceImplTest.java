package ru.nc.portal.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.nc.portal.model.Course;
import ru.nc.portal.model.dto.CourseDTO;
import ru.nc.portal.repository.CourseRepository;
import ru.nc.portal.service.CourseService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application.test.yaml")
@Sql("/data-h2.sql")
@ActiveProfiles("test")
public class CourseServiceImplTest {

    @TestConfiguration
    static class CourseServiceImplTestContextConfiguration {

        @Bean
        public CourseService courseService() {
            return new CourseServiceImpl();
        }
    }

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @Before
    public void setUp() {
    }
    @Test
    public void whenGetByName_ListOfCoursesShouldBeFound() {
        String name = "J";
        List<Course> found = courseService.getCoursesByName(name);
        assertThat(found.get(0).getName()).isEqualTo("Java in action");
    }


//    @Test
//    public void getALLTest() {
//        Assertions.assertThat(courseService.getALL().size()).isEqualTo(6);
//    }

    @Test
    public void getByIdTest() {
        Assertions.assertThat(courseService.getById(1L).getName()).isEqualTo("C for beginner");
    }

    @Test
    public void createCourseTest() {
        CourseDTO cd = new CourseDTO(10L,"new course", "some description");
        courseService.createCourse(cd);
        Assertions.assertThat(courseRepository.findByName("new course").get(0).getName()).isEqualTo(cd.getName());
    }
    @Test
    public void getCoursesByNameTest() {
        Assertions.assertThat(courseService.getCoursesByName("C for beginner").get(0).getId()).isEqualTo(1L);
    }

    @Test
    public void setLockToCourseTest() {
        courseService.setLockToCourse(1L, true);
        Assertions.assertThat(courseRepository.findByName("C for beginner").get(0).isLocked()).isEqualTo(true);
    }
}
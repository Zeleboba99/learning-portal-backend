package ru.nc.portal.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.nc.portal.model.Course;
import ru.nc.portal.model.Role;
import ru.nc.portal.model.User;
import ru.nc.portal.repository.CourseRepository;
import ru.nc.portal.repository.UserRepository;
import ru.nc.portal.service.CourseService;
import ru.nc.portal.service.UserService;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

import java.util.List;

import static org.junit.Assert.*;
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application-test.yaml")
@Sql("/data-h2.sql")
@ActiveProfiles("test")
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration{

        @Bean
        public UserService userService(){
            return new UserServiceImpl();
        }

        @Bean
        public CourseService courseService(){
            return new CourseServiceImpl();
        }
    }

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Test
    public void whenSetCourseForUser_thenUpdateRecords(){
//        userService.setCourseForUser(1L,1L);
//        List<Course> courseList = userService.getAllCoursesForUser(1L);
//        assertThat(courseList, hasItem(hasProperty("id", is(1L))));
    }
    @Test
    public void getAllCoursesForUserTest() {
        User user = userService.getUserById(1);
        Assertions.assertThat(user.getCourses().size()).isEqualTo(3);
    }


    @Test
    public void getUserByIdTest() {
        User user = userService.getUserById(1);
        Assertions.assertThat(user.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    public void setCourseForUserTest() {
        User user = userService.getUserById(1);
        userService.setCourseForUser(user.getId(),3L);
        Assertions.assertThat(userRepository.findUserById(user.getId()).getCourses().size()).isEqualTo(4);
    }
    @Test
    public void getCourseByIdForUserTest() {
        Assertions.assertThat(userService.getCourseByIdForUser(1L,2L).getName()).isEqualTo("Java in action");
    }
}
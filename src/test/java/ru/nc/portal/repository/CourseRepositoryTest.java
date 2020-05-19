package ru.nc.portal.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nc.portal.model.Course;

import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    public TestEntityManager entityManager;

    @Autowired
    public CourseRepository courseRepository;
    @Test
    public void whenFindByName_thenReturnListCourses() {
        Course course = new Course("java", "description");
        entityManager.persist(course);
        entityManager.flush();

        List<Course> found = courseRepository.findByName("ja");

        assertThat(found.get(0).getName()).isEqualTo(course.getName());

    }
}
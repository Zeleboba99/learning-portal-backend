package ru.nc.portal.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.nc.portal.model.Lesson;
import ru.nc.portal.repository.LessonRepository;
import ru.nc.portal.service.LessonService;
import ru.nc.portal.service.SubjectService;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application.test.yaml")
@Sql("/data-h2.sql")
@ActiveProfiles("test")
public class LessonServiceImplTest {
    @Autowired
    private LessonService lessonService;

    @Autowired
    private LessonRepository lessonRepository;
    @Test
    public void getAllLessonsForCourseTest() {
        Assertions.assertThat(lessonService.getAllLessonsForCourse(1L).size()).isEqualTo(2);
    }
    @Test
    public void getLessonByIdTest() {
        Assertions.assertThat(lessonService.getLessonById(1L).getName()).isEqualTo("First C lesson");
    }
    @Test
    public void createLessonForCourseTest() {
        Lesson lesson = new Lesson(10, "some name", "description");
        lessonService.createLessonForCourse(1L, lesson, 1L);
        Assertions.assertThat(lessonService.getLessonById(10L).getName()).isEqualTo("some name");
    }
    @Test
    public void deleteLessonByIdTest() {
        Lesson lesson = new Lesson(11, "some name", "description");
        lessonService.createLessonForCourse(1L, lesson, 1L);
        Assertions.assertThat(lessonService.getAllLessonsForCourse(1L).size()).isEqualTo(3);
        lessonService.deleteLessonById(11L, 1L);
        Assertions.assertThat(lessonService.getAllLessonsForCourse(1L).size()).isEqualTo(2);
    }
}

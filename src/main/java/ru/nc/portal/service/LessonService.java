package ru.nc.portal.service;

import ru.nc.portal.model.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> getAllLessonsForCourse(Long course_id);
    Lesson getLessonById(Long lesson_id);
    Lesson createLessonForCourse(Long course_id, Lesson lesson, Long user_id);
    Lesson updateLessonForCourse(Long course_id, Lesson lesson, Long user_id);
    void deleteLessonById(Long lesson_id, Long user_id);
}

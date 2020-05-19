package ru.nc.portal.service;

import org.springframework.data.domain.Page;
import ru.nc.portal.model.Course;
import ru.nc.portal.model.Subject;
import ru.nc.portal.model.dto.CourseDTO;

import java.util.List;

public interface CourseService {

    Page<Course> getALL(int page, int size, boolean sort);
    Page<Course> getAll(int page, int size, boolean locked, boolean sort);
    Course getById(Long id);
    Course createCourse(CourseDTO courseDTO);
    Page<Course> getBySubjectId(Long subjectId, boolean locked, int page, int size, boolean sort);
    List<Course> getCoursesByName(String name);
    Page<Course> getAllByUserId(Long id, boolean locked, int page, int size, boolean sort);
    List<Course> getPassedCourses(Long user_id, boolean locked);
    Course setLockToCourse(Long course_id, boolean locked);
    void setThresholdForCourse(Long course_id, Integer threshold);
    void deleteCourse(Long course_id, Long user_id);
}


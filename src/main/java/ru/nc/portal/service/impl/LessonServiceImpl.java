package ru.nc.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.nc.portal.exception.ResourceNotFoundException;
import ru.nc.portal.model.Course;
import ru.nc.portal.model.Lesson;
import ru.nc.portal.model.Role;
import ru.nc.portal.model.User;
import ru.nc.portal.repository.CourseRepository;
import ru.nc.portal.repository.LessonRepository;
import ru.nc.portal.repository.UserRepository;
import ru.nc.portal.service.LessonService;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Lesson> getAllLessonsForCourse(Long course_id) {
        return lessonRepository.getAllLessonsByCourseIdOrderByNumberAsc(course_id);
    }

    @Override
    public Lesson getLessonById(Long lesson_id) {
        return lessonRepository.findById(lesson_id).orElse(null);
    }

    @Override
    public Lesson createLessonForCourse(Long course_id, Lesson lesson, Long user_id) {
        Course course = courseRepository.findById(course_id).orElse(null);
        if (course!=null && (isUserAuthorOfCourse(user_id, course_id) || isUserAdmin(user_id))) {
            List<Lesson> lessons = lessonRepository.getAllByNumberIsGreaterThanEqual(lesson.getNumber());
            for (Lesson x : lessons) {
                x.setNumber(x.getNumber() + 1);
            }
            lessonRepository.saveAll(lessons);
            lesson.setCourse(course);
            return lessonRepository.save(lesson);
        }
        return null;
    }

    @Override
    public Lesson updateLessonForCourse(Long course_id, Lesson lesson, Long user_id) {
        Course course = courseRepository.findById(course_id).orElseThrow(()-> new ResourceNotFoundException("Course", "id", course_id));
        if (isUserAuthorOfCourse(user_id, course_id) || isUserAdmin(user_id)) {
            lesson.setCourse(course);
            return lessonRepository.save(lesson);
        }
        return null;
    }

    @Override
    public void deleteLessonById(Long lesson_id, Long user_id) {
        Lesson lesson = lessonRepository.findById(lesson_id).orElseThrow(()->new ResourceNotFoundException("Lesson", "id", lesson_id));
        if (isUserAuthorOfCourse(user_id, lesson.getCourse().getId()) || isUserAdmin(user_id)) {
            List<Lesson> lessons = lessonRepository.getAllByNumberIsGreaterThanEqual(lesson.getNumber());
            for(Lesson x: lessons){
                x.setNumber(x.getNumber()-1);
            }
            lessonRepository.deleteById(lesson_id);
        }
    }

    private boolean isUserAuthorOfCourse(Long user_id, Long course_id) {
        Optional<Course> courseOptional = courseRepository.findById(course_id);
        return courseOptional.isPresent() && courseOptional.get().getAuthorOfCourse().equals(user_id);
    }

    private boolean isUserAdmin(Long user_id){
        Optional<User> userOptional = userRepository.findById(user_id);
        if (userOptional.isPresent()){
            Optional<Role> roleOptional = userOptional.get().getRoles().stream().filter(x -> x.getName().equals("ROLE_ADMIN")).findFirst();
            return roleOptional.isPresent();
        }
        return false;
    }
}

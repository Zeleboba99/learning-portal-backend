package ru.nc.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.nc.portal.exception.ResourceNotFoundException;
import ru.nc.portal.model.Course;
import ru.nc.portal.model.Subject;
import ru.nc.portal.model.User;
import ru.nc.portal.model.dto.CourseDTO;
import ru.nc.portal.model.dto.SubjectDTO;
import ru.nc.portal.repository.CourseRepository;
import ru.nc.portal.repository.SubjectRepository;
import ru.nc.portal.service.CourseService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "courseServiceImpl")
public class CourseServiceImpl implements CourseService {

    private static final int MAX_THRESHOLD = 100;
    private static final int MIN_THRESHOLD = 50;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<Course> getALL(int page, int size, boolean sort) {
        if (!sort)
            return courseRepository.findAll(PageRequest.of(page, size, Sort.by("name").descending()));
        return courseRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    @Override
    public Page<Course> getAll(int page, int size, boolean locked, boolean sort) {
        if (!sort)
            return courseRepository.findAllByLocked(locked, PageRequest.of(page, size, Sort.by("name").descending()));
        return courseRepository.findAllByLocked(locked, PageRequest.of(page, size, Sort.by("name")));
    }

    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Course", "id", id));
    }

    @Override
    public Course createCourse(CourseDTO courseDTO) {
        Course course =new Course();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        courseRepository.save(course);
        return course;
    }

    @Override
    public Page<Course> getBySubjectId(Long subjectId, boolean locked, int page, int size, boolean sort) {
        if (!sort)
            return courseRepository.findAllBySubjectIdAndLocked(Math.toIntExact(subjectId), locked, PageRequest.of(page, size, Sort.by("name").descending()));
        return courseRepository.findAllBySubjectIdAndLocked(Math.toIntExact(subjectId), locked, PageRequest.of(page, size, Sort.by("name")));
    }

    @Override
    public List<Course> getCoursesByName(String name) {
        return courseRepository.findByName(name);
    }

    @Override
    public Page<Course> getAllByUserId(Long id, boolean locked, int page, int size, boolean sort) {
        if (!sort)
            return courseRepository.getAllByAuthorOfCourseAndLocked(id, locked, PageRequest.of(page, size, Sort.by("name").descending()));
        return courseRepository.getAllByAuthorOfCourseAndLocked(id, locked, PageRequest.of(page, size, Sort.by("name")));
    }

    @Override
    public List<Course> getPassedCourses(Long user_id, boolean locked) {
        return courseRepository.getSuccessfullyPassedCourses(user_id).stream().filter(x->x.isLocked()==locked).collect(Collectors.toList());
    }

    @Override
    public Course setLockToCourse(Long course_id, boolean locked) {
        Course course = courseRepository.findById(course_id).orElseThrow(()->new ResourceNotFoundException("Course", "id", course_id));
        course.setLocked(locked);
        return courseRepository.save(course);
    }

    @Override
    public void setThresholdForCourse(Long course_id, Integer threshold) {
        Course course = courseRepository.findById(course_id).orElseThrow(() -> new ResourceNotFoundException("course", "id", course_id));
        if (threshold >= MIN_THRESHOLD && threshold <= MAX_THRESHOLD) {
            course.setThreshold(threshold);
            courseRepository.save(course);
        }
        else
            throw new RuntimeException("invalid value of threshold");
    }

    @Override
    public void deleteCourse(Long course_id, Long user_id) {
        if (isUserAuthorOfCourse(user_id, course_id)) {
            Course course = courseRepository.getOne(course_id);
            for (User user : course.getUsers()) {
                user.setCourses(user.getCourses().stream().filter(x -> !x.getId().equals(course_id)).collect(Collectors.toList()));
            }
            courseRepository.deleteById(course_id);
        }
    }

    private boolean isUserAuthorOfCourse(Long user_id, Long course_id) {
        Optional<Course> courseOptional = courseRepository.findById(course_id);
        return courseOptional.isPresent() && courseOptional.get().getAuthorOfCourse().equals(user_id);
    }
}

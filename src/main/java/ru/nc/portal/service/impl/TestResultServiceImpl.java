package ru.nc.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nc.portal.model.Course;
import ru.nc.portal.model.TestResult;
import ru.nc.portal.model.User;
import ru.nc.portal.repository.CourseRepository;
import ru.nc.portal.repository.TestResultRepository;
import ru.nc.portal.repository.UserRepository;
import ru.nc.portal.service.TestResultService;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TestResultServiceImpl implements TestResultService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Override
    public void addNewTestResult(TestResult testResult, Long user_id, Long course_id) {
        Optional<User> userOptional = userRepository.findById(user_id);
        Optional<Course> courseOptional = courseRepository.findById(course_id);
        if (courseOptional.isPresent() && userOptional.isPresent()) {
            Integer num = testResultRepository.getMaxNumberOfResult(user_id, course_id);
            if (num == null)
                num = 0;
            testResult.setNumber(num + 1);
            testResult.setPassedAt(new Date());
            testResult.setUser(userOptional.get());
            testResult.setCourse(courseOptional.get());
            testResultRepository.save(testResult);
        }
    }

    @Override
    public List<TestResult> getAllTestResults(Long user_id, Long course_id) {
        return testResultRepository.findAllByUserIdAndCourseId(user_id, course_id);
    }

    private boolean isUserAuthorOfCourse(Long user_id, Long course_id) {
        Optional<Course> courseOptional = courseRepository.findById(course_id);
        return courseOptional.isPresent() && courseOptional.get().getAuthorOfCourse().equals(user_id);
    }
}

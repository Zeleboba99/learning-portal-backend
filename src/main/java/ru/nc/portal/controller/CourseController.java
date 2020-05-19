package ru.nc.portal.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.nc.portal.model.Course;
import org.springframework.web.bind.annotation.*;
import ru.nc.portal.model.Subject;
import ru.nc.portal.model.TestResult;
import ru.nc.portal.model.User;
import ru.nc.portal.model.dto.CourseDTO;
import ru.nc.portal.model.dto.SubjectDTO;
import ru.nc.portal.model.dto.TestResultDTO;
import ru.nc.portal.repository.CourseRepository;
import ru.nc.portal.repository.SubjectRepository;
import ru.nc.portal.repository.UserCourseRepository;
import ru.nc.portal.repository.UserRepository;
import ru.nc.portal.security.CurrentUser;
import ru.nc.portal.security.UserPrincipal;
import ru.nc.portal.service.CourseService;
import ru.nc.portal.service.SubjectService;
import ru.nc.portal.service.TestResultService;
import ru.nc.portal.service.UserService;

import javax.validation.Valid;
import java.io.Console;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api")
public class CourseController {
    @Autowired
    public UserCourseRepository userCourseRepository;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public TestResultService testResultService;

    @Autowired
    UserService userService;

    @Autowired
    public SubjectRepository subjectRepository;

    @Autowired
    public CourseRepository courseRepository;

    @Autowired
    public CourseService courseService;

    @Autowired
    public SubjectService subjectService;

    @Autowired
    public UserRepository userRepository;

    @GetMapping(
            value = "/courses",
            params = {"page", "size"}
    )
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Course> getAll(@RequestParam("page") int page, @RequestParam("size") int size,
                               @RequestParam(value = "sort", required = false) String sort) {
        if (sort.toLowerCase().equals("desc"))
            return courseService.getALL(page, size, false);
        else
            return courseService.getALL(page, size, true);
    }

    @GetMapping(
            value = "/courses/unlocked",
            params = {"page", "size"}
    )
    public Page<Course> getAllUnlocked(@RequestParam("page") int page, @RequestParam("size") int size,
                                       @RequestParam(value = "sort", required = false) String sort) {
        if (sort.toLowerCase().equals("desc"))
            return courseService.getAll(page, size, false, false);
        return courseService.getAll(page, size, false, true);
    }

    @GetMapping(
            value = "/courses/locked",
            params = {"page", "size"}
    )
    public Page<Course> getAllLockedCourses(@RequestParam("page") int page, @RequestParam("size") int size,
                                            @RequestParam(value = "sort", required = false) String sort) {
        if (sort.toLowerCase().equals("desc"))
            return courseService.getAll(page, size, true, false);
        return courseService.getAll(page, size, true, true);
    }

    @GetMapping(value = "/courses/{id}")
    public Course getCourseById(@PathVariable("id") Long courseId) {
        return courseService.getById(courseId);
    }


    @GetMapping(value = "/courses2/{id}")
    public Course getCourse2(@PathVariable("id") Long courseId) {
        Course course = courseService.getById(courseId);
        User user = userRepository.findUserById(course.getAuthorOfCourse());
        return course;
    }

    @GetMapping(
            value = "/courses/by-subject-id/{id}",
            params = {"page", "size"}
    )
    public Page<Course> getCoursesBySubjectId(@PathVariable("id") Long subjectId,
                                              @RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam(value = "sort", required = false) String sort) {
        if (sort.toLowerCase().equals("desc"))
            return courseService.getBySubjectId(subjectId, false, page, size, false);
        return courseService.getBySubjectId(subjectId, false, page, size, true);
    }

    @PostMapping(value = "/courses/create-course")
    @PreAuthorize("hasRole('USER')")
    public Course createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        String subName = courseDTO.getSubject().getName();
        String s = subName.substring(0, 1).toUpperCase();
        String subName2 = s + subName.substring(1);
        System.out.println(subName2);
        Subject ss = subjectRepository.findByName(subName2);
        User user = userRepository.findUserById(courseDTO.getAuthorOfCourse());
        Long userId = user.getId();
        if (ss == null) {
            Subject s1 = subjectService.createSubject(new SubjectDTO(subName2));
            Course newCourse = new Course(courseDTO.getName(), courseDTO.getDescription(), s1, false, userId);
            return courseRepository.save(newCourse);
        } else {
            Course newCourse = new Course(courseDTO.getName(), courseDTO.getDescription(), ss, false, userId);
            return courseRepository.save(newCourse);
        }
    }

    @GetMapping(value = "/courses/by-name/{name}")
    public List<Course> getCoursesByName(@PathVariable("name") String name) {
        return courseService.getCoursesByName(name);
    }

    @GetMapping(
            value = "/courses/courses-by-user-id/{id}",
            params = { "page", "size" }
    )
    @PreAuthorize("hasRole('USER')")
    public Page<Course> getAllCoursesByUserId(@PathVariable("id") Long id,
                                              @RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam(value = "sort", required = false) String sort) {
        if (sort.toLowerCase().equals("desc"))
            return courseService.getAllByUserId(id, false, page, size, false);
        return courseService.getAllByUserId(id, false, page, size, true);
    }


    @GetMapping(value = "/courses/{course_id}/test-results")
    @PreAuthorize("hasRole('USER')")
    public List<TestResult> getTestResultsForCourse (@CurrentUser UserPrincipal userPrincipal,
            @PathVariable("course_id") Long course_id){
        return testResultService.getAllTestResults(userPrincipal.getId(), course_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/courses/{course_id}/test-results")
      @PreAuthorize("hasRole('USER')")
    public void createTestResult (@CurrentUser UserPrincipal userPrincipal,
            @PathVariable("course_id") Long course_id,
            @Valid @RequestBody TestResultDTO testResultDTO){
        TestResult testResult = convertPageDTOToEntity(testResultDTO);
        testResultService.addNewTestResult(testResult, userPrincipal.getId(), course_id);
    }

    @GetMapping(value = "/courses/passed")
    @PreAuthorize("hasRole('USER')")
    public List<Course> getPassedCourses (@CurrentUser UserPrincipal userPrincipal){
        return courseService.getPassedCourses(userPrincipal.getId(), false);
    }

    @PatchMapping(value = "/courses/{course_id}/lock")
    @PreAuthorize("hasRole('ADMIN')")
    public Course lockCourse (@CurrentUser UserPrincipal userPrincipal,
            @PathVariable("course_id") Long course_id,@Valid @RequestBody boolean lock){
        return courseService.setLockToCourse(course_id, lock);
    }

    private TestResult convertPageDTOToEntity (TestResultDTO testResultDTO){
        return modelMapper.map(testResultDTO, TestResult.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/courses/delete-course/{id}")
    @PreAuthorize("hasRole('USER')")
    public void deleteCourseForUser (@PathVariable("id") Long id,
            @CurrentUser UserPrincipal userPrincipal){
        userCourseRepository.deleteUserCourse(userPrincipal.getId(), id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/courses/{course_id}")
    @PreAuthorize("hasRole('USER')")
    public void deleteCourse (@PathVariable("course_id") Long course_id,
                                     @CurrentUser UserPrincipal userPrincipal){
        courseService.deleteCourse(course_id, userPrincipal.getId());
    }
}

package ru.nc.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nc.portal.exception.ResourceNotFoundException;
import ru.nc.portal.model.Course;
import ru.nc.portal.model.Role;
import ru.nc.portal.model.User;
import ru.nc.portal.repository.CourseRepository;
import ru.nc.portal.repository.RoleRepository;
import ru.nc.portal.repository.UserRepository;
import ru.nc.portal.security.UserPrincipal;
import ru.nc.portal.service.CourseService;
import ru.nc.portal.service.UserService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public User getCurrentUser(UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @Override
    public void setCourseForUser(Long user_id, Long course_id) {
        Course course = courseService.getById(course_id);
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        user.getCourses().add(course);
        course.getUsers().add(user);
        userRepository.save(user);
    }

    @Override
    public Course getCourseByIdForUser(Long user_id, Long course_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        return user.getCourses().stream().filter(course -> course.getId().equals(course_id)).findFirst().orElse(null);
    }

    @Override
    public Page<Course> getAllCoursesForUser(Long user_id, int page, int size, boolean sort) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
        List<Course> courses = new ArrayList<>();
        if (sort)
            courses = user.getCourses().stream().sorted(Comparator.comparing(Course::getName)).collect(Collectors.toList());
        else
            courses = user.getCourses().stream().sorted(Comparator.comparing(Course::getName).reversed()).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > courses.size() ? courses.size() : (start + pageable.getPageSize());
        Page<Course> pages = new PageImpl<Course>(courses.subList(start, end), pageable, courses.size());
        return pages;
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User updateProfileImageForUser(Long user_id, MultipartFile file) throws IOException {
        User user = userRepository.findUserById(user_id);
        user.setImage(file.getBytes());
        return userRepository.save(user);
    }

    @Override
    public List<Role> getCurrentRoles(UserPrincipal userPrincipal) {
        return (List<Role>) userRepository.getOne(userPrincipal.getId()).getRoles();
    }

    @Override
    public Page<User> getAll(int page, int size, boolean sort) {
        if (!sort)
            return userRepository.findAll(PageRequest.of(page, size, Sort.by("name").descending()));
        return userRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    @Override
    public void changeRole(Long user_id, String[] roleNames) {
        List<Role> newRoles = new ArrayList<>();
        for(String roleName: roleNames) {
            Role role = roleRepository.findByName(roleName);
            if (role != null)
                newRoles.add(roleRepository.findByName(roleName));
        }
        if (!newRoles.isEmpty()){
            User user = userRepository.findUserById(user_id);
            user.setRoles(newRoles);
            userRepository.save(user);
        }
    }
}

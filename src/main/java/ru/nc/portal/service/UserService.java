package ru.nc.portal.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ru.nc.portal.model.Course;
import ru.nc.portal.model.Role;
import ru.nc.portal.model.User;
import ru.nc.portal.security.UserPrincipal;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface UserService {
    User getCurrentUser(UserPrincipal userPrincipal);

    void setCourseForUser(Long user_id, Long course_id);

    Course getCourseByIdForUser(Long user_id, Long course_id);

    Page<Course> getAllCoursesForUser(Long user_id, int page, int size, boolean sort);

    User getUserById(long id);

    User updateProfileImageForUser(Long user_id, MultipartFile file) throws IOException;

    List<Role> getCurrentRoles(UserPrincipal userPrincipal);

    Page<User> getAll(int page, int size, boolean sort);

    void changeRole(Long user_id, String[] roleNames);
}

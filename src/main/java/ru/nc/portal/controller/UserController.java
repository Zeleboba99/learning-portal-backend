package ru.nc.portal.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nc.portal.exception.ResourceNotFoundException;
import ru.nc.portal.model.Course;
import ru.nc.portal.model.Role;
import ru.nc.portal.model.User;
import ru.nc.portal.repository.UserRepository;
import ru.nc.portal.security.CurrentUser;
import ru.nc.portal.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.nc.portal.service.RoleService;
import ru.nc.portal.service.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    public ModelMapper modelMapper;

    @GetMapping("/users/by-id/{id}")
    public User getUserById(@PathVariable("id")Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/me")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getCurrentUser(userPrincipal);
    }

    @GetMapping("/users/me/roles")
    public List<Role> getCurrentRoles(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getCurrentRoles(userPrincipal);
    }

    @PostMapping("/users/me/courses/{course_id}")
    @PreAuthorize("hasRole('USER')")
    public void addCourseForUser(@CurrentUser UserPrincipal userPrincipal, @PathVariable("course_id") Long course_id){
        userService.setCourseForUser(userPrincipal.getId(), course_id);
    }

    @GetMapping(value = "/users/me/courses/{course_id}")
    @PreAuthorize("hasRole('USER')")
    public Course getCourseByIdForUser(@CurrentUser UserPrincipal userPrincipal,
                                       @PathVariable("course_id") Long course_id) {
        return userService.getCourseByIdForUser(userPrincipal.getId(), course_id);
    }

    @GetMapping(
            value = "/users/me/courses",
            params = {"page", "size"}
    )
    @PreAuthorize("hasRole('USER')")
    public Page<Course> getAllCoursesForUser(@CurrentUser UserPrincipal userPrincipal,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size,
                                             @RequestParam(value = "sort", required = false) String sort){
        if (sort.toLowerCase().equals("desc"))
            return userService.getAllCoursesForUser(userPrincipal.getId(), page, size, false);
        return userService.getAllCoursesForUser(userPrincipal.getId(), page, size, true);
    }

    //    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PutMapping("/users/me")
//    @PreAuthorize("hasRole('USER')")
//    public void updateUser(@CurrentUser UserPrincipal userPrincipal,
//                           @Valid @RequestBody UserDTO userDTO){
//        userService.updateUser(convertUserDTOToEntity(userDTO));
//    }
//
//    private User convertUserDTOToEntity(UserDTO userDTO) {
//        return modelMapper.map(userDTO, User.class);
//    }
    @PatchMapping("/users/me/change-image")
    @PreAuthorize("hasRole('USER')")
    public User updateImageForUser(@RequestParam("profile_image") MultipartFile file,
                                   @CurrentUser UserPrincipal userPrincipal) throws IOException {
        return userService.updateProfileImageForUser(userPrincipal.getId(), file);
    }

    @GetMapping(
            value = "/users",
            params = {"page", "size"}
    )
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getAll(@RequestParam("page") int page, @RequestParam("size") int size,
                             @RequestParam(value = "sort", required = false) String sort){
        if (sort.toLowerCase().equals("desc"))
            return userService.getAll(page, size, false);
        return userService.getAll(page, size, true);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(
            value = "/users/{user_id}/change-role"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public void changeUserRole(@PathVariable("user_id")Long user_id,
                               @RequestBody String[] roles,
                               @CurrentUser UserPrincipal userPrincipal) {
        if (!userPrincipal.getId().equals(user_id))
            userService.changeRole(user_id, roles);
    }

    @GetMapping("/users/roles")
    public List<Role> getAllRoles(){
        return roleService.getAll();
    }
}

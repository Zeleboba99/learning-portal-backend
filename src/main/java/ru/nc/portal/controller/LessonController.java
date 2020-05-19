package ru.nc.portal.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nc.portal.model.Lesson;
import ru.nc.portal.model.dto.LessonDTO;
import ru.nc.portal.security.CurrentUser;
import ru.nc.portal.security.UserPrincipal;
import ru.nc.portal.service.LessonService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class LessonController {

    @Autowired
    public LessonService lessonService;

    @Autowired
    public ModelMapper modelMapper;

    @GetMapping(value = "/courses/{course_id}/lessons")
    public List<Lesson> getAllLessonsForCourse(@PathVariable("course_id") Long course_id){
        return lessonService.getAllLessonsForCourse(course_id);
    }

    @GetMapping(value = "/lessons/{lesson_id}")
    public Lesson getLessonById(@PathVariable("lesson_id") Long lesson_id) {
        return lessonService.getLessonById(lesson_id);
    }

    @PostMapping(value = "/courses/{course_id}/lessons")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Lesson> createLessonForCourse(@PathVariable("course_id") Long course_id,
                                                        @Valid @RequestBody LessonDTO lessonDTO,
                                                        @CurrentUser UserPrincipal userPrincipal){
        Lesson lesson = lessonService.createLessonForCourse(course_id, convertLessonDTOToEntity(lessonDTO), userPrincipal.getId());
        return new ResponseEntity<>(lesson, HttpStatus.CREATED);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/courses/{course_id}/lessons")
    @PreAuthorize("hasRole('USER')")
    public void updateLesson(@PathVariable("course_id") Long course_id,
                             @Valid @RequestBody LessonDTO lessonDTO,
                             @CurrentUser UserPrincipal userPrincipal){
        lessonService.updateLessonForCourse(course_id, convertLessonDTOToEntity(lessonDTO), userPrincipal.getId());
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/lessons/{lesson_id}")
    @PreAuthorize("hasRole('USER')")
    public void deleteLesson(@PathVariable("lesson_id") Long lesson_id,
                             @CurrentUser UserPrincipal userPrincipal){
        lessonService.deleteLessonById(lesson_id, userPrincipal.getId());
    }

    private Lesson convertLessonDTOToEntity(LessonDTO lessonDTO){
        return modelMapper.map(lessonDTO, Lesson.class);
    }
}

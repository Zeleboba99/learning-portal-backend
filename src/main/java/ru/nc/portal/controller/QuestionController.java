package ru.nc.portal.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nc.portal.model.Question;
import ru.nc.portal.model.dto.QuestionDTO;
import ru.nc.portal.security.CurrentUser;
import ru.nc.portal.security.UserPrincipal;
import ru.nc.portal.service.CourseService;
import ru.nc.portal.service.QuestionService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class QuestionController {

    @Autowired
    public QuestionService questionService;

    @Autowired
    public CourseService courseService;

    @Autowired
    public ModelMapper modelMapper;

    @GetMapping(value = "/courses/{course_id}/questions")
    @PreAuthorize("hasRole('USER')")
    public List<Question> getAllQuestionForCourse(@PathVariable("course_id") Long course_id){
        return questionService.getAllQuestionsForCourse(course_id);
    }

    @GetMapping(value = "/questions/{question_id}")
    @PreAuthorize("hasRole('USER')")
    public Question getQuestionById(@PathVariable("question_id") Long question_id){
        return questionService.getQuestionById(question_id);
    }

    @PostMapping(value = "/courses/{course_id}/questions")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Question> createQuestion(@PathVariable("course_id") Long course_id, @Valid @RequestBody QuestionDTO questionDTO,
                                                   @CurrentUser UserPrincipal userPrincipal){
        return new ResponseEntity<>(questionService.createQuestionForCourse(course_id, convertQuestionDTOToEntity(questionDTO), userPrincipal.getId()), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/courses/{course_id}/questions")
    @PreAuthorize("hasRole('USER')")
    public void updateQuestion(@PathVariable("course_id") Long course_id,
                               @Valid @RequestBody QuestionDTO questionDTO,
                               @CurrentUser UserPrincipal userPrincipal){
        questionService.updateQuestion(course_id, convertQuestionDTOToEntity(questionDTO), userPrincipal.getId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/questions/{question_id}")
    @PreAuthorize("hasRole('USER')")
    public void deleteQuestionById(@PathVariable("question_id") Long question_id, @CurrentUser UserPrincipal userPrincipal)
    {
        questionService.deleteById(question_id, userPrincipal.getId());
    }


    @PostMapping(value = "/courses/{course_id}/tests")
    @PreAuthorize("hasRole('USER')")
    public List<Question> createTest(@PathVariable("course_id") Long course_id,
                                     @RequestParam(value = "threshold", required = true) Integer threshold,
                                     @Valid @RequestBody List<QuestionDTO> questionDTOListDTO,
                                     @CurrentUser UserPrincipal userPrincipal){
        List<Question> questions = questionDTOListDTO.stream().map(this::convertQuestionDTOToEntity).collect(Collectors.toList());
        courseService.setThresholdForCourse(course_id, threshold);
        return questionService.createTest(course_id, questions, userPrincipal.getId());
    }

    private Question convertQuestionDTOToEntity(QuestionDTO questionDTO){
        return modelMapper.map(questionDTO, Question.class);
    }

}

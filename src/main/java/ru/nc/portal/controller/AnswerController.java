package ru.nc.portal.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.nc.portal.model.Answer;
import ru.nc.portal.model.dto.AnswerDTO;
import ru.nc.portal.service.AnswerService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class AnswerController {

    @Autowired
    public AnswerService answerService;

    @Autowired
    public ModelMapper modelMapper;

    @GetMapping(value = "/questions/{question_id}/answers")
    @PreAuthorize("hasRole('USER')")
    public List<Answer> getAllAnswersForQuestion(@PathVariable("question_id") Long question_id) {
        return answerService.getAllAnswersForQuestion(question_id);
    }

    @PostMapping(value = "/questions/{question_id}/answers")
    @PreAuthorize("hasRole('USER')")
    public List<Answer> setAnswersForQuestion(@PathVariable("question_id") Long question_id,
                                              @Valid @RequestBody List<AnswerDTO> answerDTOList) {
        List<Answer> answers = new ArrayList<>();
        for (AnswerDTO answerDTO: answerDTOList){
            answers.add(convertAnswerDTOToEntity(answerDTO));
        }
        return answerService.setAnswersForQuestion(question_id, answers);
    }

    @PutMapping(value = "/questions/{question_id}/answers")
    @PreAuthorize("hasRole('USER')")
    public List<Answer> updateAllAnswersForQuestion(@PathVariable("question_id") Long question_id,
                                              @Valid @RequestBody List<AnswerDTO> answerDTOList) {
        List<Answer> answers = new ArrayList<>();
        for (AnswerDTO answerDTO: answerDTOList){
            answers.add(convertAnswerDTOToEntity(answerDTO));
        }
        return answerService.setAnswersForQuestion(question_id, answers);
    }
    private Answer convertAnswerDTOToEntity(AnswerDTO answerDTO){
        return modelMapper.map(answerDTO, Answer.class);
    }
}
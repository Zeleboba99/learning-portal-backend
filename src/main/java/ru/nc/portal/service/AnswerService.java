package ru.nc.portal.service;

import ru.nc.portal.model.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> getAllAnswersForQuestion(Long question_id);

    List<Answer> setAnswersForQuestion(Long question_id, List<Answer> answers);

}

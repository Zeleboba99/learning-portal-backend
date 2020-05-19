package ru.nc.portal.service;

import ru.nc.portal.model.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestionsForCourse(Long course_id);

    Question createQuestionForCourse(Long course_id, Question question, Long user_id);

    void updateQuestion(Long course_id, Question question, Long user_id);

    void deleteById(Long question_id, Long user_id);

    Question getQuestionById(Long question_id);

    List<Question> createTest(Long course_id, List<Question> questions, Long user_id);
}

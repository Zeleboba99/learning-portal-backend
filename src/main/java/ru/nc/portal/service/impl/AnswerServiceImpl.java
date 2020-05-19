package ru.nc.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nc.portal.model.Answer;
import ru.nc.portal.model.Question;
import ru.nc.portal.repository.AnswerRepository;
import ru.nc.portal.repository.QuestionRepository;
import ru.nc.portal.service.AnswerService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Answer> getAllAnswersForQuestion(Long question_id) {
        return answerRepository.findAllByQuestion_Id(question_id);
    }

    @Override
    @Transactional
    public List<Answer> setAnswersForQuestion(Long question_id, List<Answer> answers) {
        answerRepository.deleteAllByQuestion_Id(question_id);
        Question question = questionRepository.findById(question_id).orElse(null);
        answers.forEach(x -> x.setQuestion(question));
        return answerRepository.saveAll(answers);
    }
}

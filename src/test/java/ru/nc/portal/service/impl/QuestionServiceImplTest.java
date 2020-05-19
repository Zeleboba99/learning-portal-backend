package ru.nc.portal.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.nc.portal.model.Question;
import ru.nc.portal.repository.AnswerRepository;
import ru.nc.portal.service.QuestionService;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application.test.yaml")
@Sql("/data-h2.sql")
@ActiveProfiles("test")
public class QuestionServiceImplTest {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerRepository answerRepository;
    @Test
    public void getAllQuestionsForCourseTest() {
        Assertions.assertThat(questionService.getAllQuestionsForCourse(1L).size()).isEqualTo(2);
    }
    @Test
    public void createQuestionForCourseTest() {
        Question question = new Question();
        questionService.createQuestionForCourse(1L,question,1L);
        Assertions.assertThat(questionService.getAllQuestionsForCourse(1L).size()).isEqualTo(3);
    }
    @Test
    public void updateQuestionTest() {
        Question question = questionService.getQuestionById(1L);
        question.setContent("new content");
        questionService.updateQuestion(1L,question,1L);
        Assertions.assertThat(questionService.getAllQuestionsForCourse(1L).get(0).getContent()).isEqualTo("new content");
    }

    @Test
    public void deleteByIdTest() {
        answerRepository.deleteAllByQuestion_Id(1L);
        answerRepository.deleteAllByQuestion_Id(2L);
        questionService.deleteById(1L,1L);
        Assertions.assertThat(questionService.getAllQuestionsForCourse(1L).size()).isEqualTo(1);
    }
    @Test
    public void getQuestionByIdTest() {
        Assertions.assertThat(questionService.getQuestionById(1L).getContent()).isEqualTo("Do you like C?");
    }
}

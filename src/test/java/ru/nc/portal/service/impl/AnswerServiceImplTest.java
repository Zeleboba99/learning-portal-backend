package ru.nc.portal.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.nc.portal.model.Answer;
import ru.nc.portal.service.AnswerService;

import java.util.ArrayList;
import java.util.List;
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application.test.yaml")
@Sql("/data-h2.sql")
@ActiveProfiles("test")
public class AnswerServiceImplTest {

    @Autowired
    private AnswerService answerService;

    @Test
    public void getAllAnswersForQuestionTest() {
        Assertions.assertThat(answerService.getAllAnswersForQuestion(1L).get(0).getContent()).isEqualTo("Yes");
    }

    @Test
    public void setAnswersForQuestionTest() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("",true,null));
        answerService.setAnswersForQuestion(1L,answers);
        Assertions.assertThat(answerService.getAllAnswersForQuestion(1L).size()).isEqualTo(1);
    }
}

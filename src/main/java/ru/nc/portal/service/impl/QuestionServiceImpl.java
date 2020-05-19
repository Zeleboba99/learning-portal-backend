package ru.nc.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nc.portal.exception.ResourceNotFoundException;
import ru.nc.portal.model.*;
import ru.nc.portal.repository.AnswerRepository;
import ru.nc.portal.repository.CourseRepository;
import ru.nc.portal.repository.QuestionRepository;
import ru.nc.portal.repository.UserRepository;
import ru.nc.portal.service.QuestionService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Question> getAllQuestionsForCourse(Long course_id) {
        return questionRepository.findAllByCourse_Id(course_id);
    }

    @Override
    public Question createQuestionForCourse(Long course_id, Question question, Long user_id) {
        if (isUserAuthorOfCourse(user_id, course_id) || isUserAdmin(user_id)) {
            question.setCourse(courseRepository.findById(course_id).orElseThrow(()->new ResourceNotFoundException("Course", "id", course_id)));
            return questionRepository.save(question);
        }
        return null;
    }

    @Override
    public void updateQuestion(Long course_id, Question question, Long user_id) {
        if (isUserAuthorOfCourse(user_id, course_id) || isUserAdmin(user_id)) {
            Question existingQuestion = questionRepository.findById(question.getId()).orElse(null);
            if (existingQuestion != null) {
                existingQuestion.setContent(question.getContent());
                questionRepository.save(existingQuestion);
            }
        }
    }

    @Override
    public void deleteById(Long question_id, Long user_id) {
        Optional<Question> optionalQuestion = questionRepository.findById(question_id);
        if (optionalQuestion.isPresent()){
            if (isUserAuthorOfCourse(user_id, optionalQuestion.get().getCourse().getId()) || isUserAdmin(user_id)){
                questionRepository.deleteById(question_id);
            }
        }
    }

    @Override
    public Question getQuestionById(Long question_id) {
        return questionRepository.findById(question_id).orElseThrow(()-> new ResourceNotFoundException("Question", "id", question_id));
    }

    @Transactional
    @Override
    public List<Question> createTest(Long course_id, List<Question> questions, Long user_id) {
        if (isUserAuthorOfCourse(user_id, course_id) || isUserAdmin(user_id)) {
            Optional<Course> course = courseRepository.findById(course_id);
            List<Answer> answers = new ArrayList<>();
            if (course.isPresent()) {
                List<Question> oldQuestions = questionRepository.findAllByCourse_Id(course.get().getId());
                oldQuestions.forEach(x -> answerRepository.deleteAllByQuestion_Id(x.getId()));
                questionRepository.deleteAll(oldQuestions);
                for (Question question : questions) {
                    question.getAnswers().forEach(x -> x.setQuestion(question));
                    answers.addAll(question.getAnswers());
                }
                questions.forEach(x -> x.setCourse(course.get()));
                questionRepository.saveAll(questions);
                answerRepository.saveAll(answers);
            }
        }
        return null;
    }

    private boolean isUserAuthorOfCourse(Long user_id, Long course_id) {
        Optional<Course> courseOptional = courseRepository.findById(course_id);
        return courseOptional.isPresent() && courseOptional.get().getAuthorOfCourse().equals(user_id);
    }

    private boolean isUserAdmin(Long user_id){
        User user = userRepository.findById(user_id).orElseThrow(()-> new ResourceNotFoundException("User", "id", user_id));
        Optional<Role> roleOptional = user.getRoles().stream().filter(x -> x.getName().equals("ROLE_ADMIN")).findFirst();
        return roleOptional.isPresent();
    }
}

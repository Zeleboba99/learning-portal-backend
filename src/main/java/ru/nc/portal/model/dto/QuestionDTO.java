package ru.nc.portal.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nc.portal.model.Course;

import java.util.List;

public class QuestionDTO {
    @JsonProperty("question_id")
    private Long id;
    @JsonProperty("content")
    private String content;
    @JsonProperty("course")
    private Course course;
    @JsonProperty("answers")
    private List<AnswerDTO> answers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}

package ru.nc.portal.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestResultDTO {
    @JsonProperty("test_result_id")
    private Long id;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("passedAt")
    private Integer passedAt;
    @JsonProperty("answersNum")
    private Integer answersNum;
    @JsonProperty("rightAnswersNum")
    private Integer rightAnswersNum;

    public TestResultDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPassedAt() {
        return passedAt;
    }

    public void setPassedAt(Integer passedAt) {
        this.passedAt = passedAt;
    }

    public Integer getAnswersNum() {
        return answersNum;
    }

    public void setAnswersNum(Integer answersNum) {
        this.answersNum = answersNum;
    }

    public Integer getRightAnswersNum() {
        return rightAnswersNum;
    }

    public void setRightAnswersNum(Integer rightAnswersNum) {
        this.rightAnswersNum = rightAnswersNum;
    }
}

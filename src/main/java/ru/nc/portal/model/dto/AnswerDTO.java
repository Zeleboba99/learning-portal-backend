package ru.nc.portal.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerDTO {
    @JsonProperty("answer_id")
    private Long id;
    @JsonProperty("content")
    private String content;
    @JsonProperty("right")
    private String isRight;

    public AnswerDTO() {
    }

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

    public String getIsRight() {
        return isRight;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }
}

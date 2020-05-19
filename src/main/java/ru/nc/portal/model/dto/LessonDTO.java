package ru.nc.portal.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LessonDTO {

    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "number")
    private Integer number;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "description")
    private String description;

    public Integer getNumber() {
        return number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

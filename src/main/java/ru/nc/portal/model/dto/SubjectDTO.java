package ru.nc.portal.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubjectDTO {
    @JsonProperty("subject_id")
    private Long id;
    @JsonProperty("subject_name")
    private String name;

    public SubjectDTO(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

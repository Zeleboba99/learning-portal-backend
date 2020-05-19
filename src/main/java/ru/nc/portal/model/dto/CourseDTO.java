package ru.nc.portal.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nc.portal.model.Subject;
import ru.nc.portal.model.User;

public class CourseDTO {
    @JsonProperty("course_id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("subject")
    private Subject subject;
    @JsonProperty("authorOfCourse")
    private Long authorOfCourse;
    @JsonProperty("locked")
    private Long locked;


    public CourseDTO() {
    }

    public CourseDTO(String name, String description, Subject subject, Long authorOfCourse) {
        this.name = name;
        this.description = description;
        this.subject = subject;
        this.authorOfCourse = authorOfCourse;
    }

    public CourseDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Long getAuthorOfCourse() {
        return authorOfCourse;
    }

    public void setAuthorOfCourse(Long authorOfCourse) {
        this.authorOfCourse = authorOfCourse;
    }
}

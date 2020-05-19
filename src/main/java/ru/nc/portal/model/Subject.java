package ru.nc.portal.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="subjects")
public class Subject {
    @Id
    @SequenceGenerator(name = "subjectSeq", sequenceName = "SUBJECT_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectSeq")
    private int id;
    @Column(name="name")
    private String name;
    @OneToMany(mappedBy = "subject")
    @JsonIgnoreProperties("subject")
    private List<Course> courses;

    public Subject(int id,String name) {
        this.id = id;
        this.name = name;
    }
    public Subject() {
    }
    public Subject(String name) {
        this.name = name;
    }

    public Subject(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

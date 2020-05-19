package ru.nc.portal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="test_results")
public class TestResult {
    @Id
    @SequenceGenerator(name = "testResultSeq", sequenceName = "TEST_RESULT_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "testResultSeq")
    private int id;
    @Column(name="num")
    private Integer number;
    @Column(name="passed_at")
    private Date passedAt;
    @Column(name="answers_num")
    private Integer answersNum;
    @Column(name="right_answers_num")
    private Integer rightAnswersNum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    private Course course;

    public TestResult() {
    }

    public TestResult(Integer number, Date passedAt, Integer answersNum, Integer rightAnswersNum, User user, Course course) {
        this.number = number;
        this.passedAt = passedAt;
        this.answersNum = answersNum;
        this.rightAnswersNum = rightAnswersNum;
        this.user = user;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getPassedAt() {
        return passedAt;
    }

    public void setPassedAt(Date passedAt) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

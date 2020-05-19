package ru.nc.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="USERS_COURSES")
public class UserCourse {
    @Id
    @Column(name="user_id")
    private Long userId;
    @Column(name = "course_id")
    private Long courseId;

    public UserCourse() {
    }

    public UserCourse(Long userId, Long courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}

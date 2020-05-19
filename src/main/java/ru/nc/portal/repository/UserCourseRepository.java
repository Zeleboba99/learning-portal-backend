package ru.nc.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nc.portal.model.UserCourse;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    void deleteUserCourseByCourseIdAndUserId(Long course_id, Long user_id);
    @Transactional
    @Modifying
    @Query("delete from UserCourse uc where uc.userId=:userId and uc.courseId=:courseId ")
    void deleteUserCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);
}

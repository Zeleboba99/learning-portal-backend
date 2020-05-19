package ru.nc.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nc.portal.model.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findAllBySubjectIdAndLocked(int subject_id, boolean locked, Pageable pageable);
    Page<Course> findAllByLocked(boolean locked, Pageable pageable);
    Page<Course> getAllByAuthorOfCourseAndLocked(Long id, boolean locked, Pageable pageable);

    @Query(value = "" +
            "select c.* " +
            "from courses c " +
            "where lower(c.name) like lower('%'||:name||'%') or " +
            "lower(c.description) like lower('%'||:name||'%')",
    nativeQuery = true)
    List<Course> findByName(@Param("name") String name);

    @Query(value = "" +
            "select c.*" +
            "from courses c " +
            "join (" +
            "select tr.course_id as course_id, " +
            "max(tr.right_answers_num*100/tr.answers_num) as max_res " +
            "from test_results tr " +
            "where tr.user_id = :user_id " +
            "group by tr.course_id) tr on " +
            "tr.course_id = c.id " +
            "where tr.max_res >= c.threshold ",
            nativeQuery = true)
    List<Course> getSuccessfullyPassedCourses(@Param("user_id") Long user_id);
}

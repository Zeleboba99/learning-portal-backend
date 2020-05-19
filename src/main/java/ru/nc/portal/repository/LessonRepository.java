package ru.nc.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nc.portal.model.Lesson;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "select l.* " +
            "from lessons l " +
            "where l.course_id = :course_id " +
            "order by l.num",
    nativeQuery = true)
    List<Lesson> getAllLessonsByCourseIdOrderByNumberAsc(@Param("course_id") Long course_id);
    List<Lesson> getAllByNumberIsGreaterThanEqual(Integer num);

}

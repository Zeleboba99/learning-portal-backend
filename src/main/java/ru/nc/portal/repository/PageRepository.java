package ru.nc.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nc.portal.model.Page;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
    @Query(value = "select p.* " +
            "from pages p " +
            "where p.lesson_id = :lesson_id",
            nativeQuery = true)
    List<Page> getAllPagesByLessonId(@Param("lesson_id") Long lesson_id);
    List<Page> findAllByLessonIdOrderByNumber(Long lesson_id);
    List<Page> getAllByNumberIsGreaterThanEqual(Integer num);
}

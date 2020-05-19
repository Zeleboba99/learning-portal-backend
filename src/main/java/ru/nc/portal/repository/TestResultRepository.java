package ru.nc.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nc.portal.model.TestResult;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    public List<TestResult> findAllByUserIdAndCourseId(Long user_id, Long course_id);
    @Query(value = "select max(tr.num) " +
            "from test_results tr " +
            "where tr.user_id = :user_id and tr.course_id = :course_id", nativeQuery = true
    )
    public Integer getMaxNumberOfResult(@Param("user_id") Long user_id, @Param("course_id") Long course_id);
}

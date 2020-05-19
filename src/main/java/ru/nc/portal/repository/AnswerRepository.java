package ru.nc.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nc.portal.model.Answer;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

     void deleteAllByQuestion_Id(Long question_id);
     List<Answer> findAllByQuestion_Id(Long question_id);
}

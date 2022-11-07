package pl.hyorinmaru.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.hyorinmaru.driver.model.Answer;

import java.util.List;

public interface AnswerRepo extends JpaRepository<Answer, Long> {

    List<Answer> findAllByQuestionId(Long questionId);
}

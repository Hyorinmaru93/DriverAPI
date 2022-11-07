package pl.hyorinmaru.driver.service;

import pl.hyorinmaru.driver.model.Answer;
import pl.hyorinmaru.driver.model.Question;
import pl.hyorinmaru.driver.payload.answer.AnswerPayload;

import java.util.List;

public interface AnswerService {

    void create(Question question, List<AnswerPayload> answerPayloadList);

    Answer readById(Long answerId);

    List<Answer> readAll();

    List<Answer> readAllByQuestionId(Long questionId);

    Answer update(AnswerPayload answerPayload, Long id);

    void delete(Long id);

}

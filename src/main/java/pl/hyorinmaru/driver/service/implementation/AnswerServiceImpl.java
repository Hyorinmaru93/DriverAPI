package pl.hyorinmaru.driver.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.hyorinmaru.driver.model.Answer;
import pl.hyorinmaru.driver.model.Question;
import pl.hyorinmaru.driver.payload.answer.AnswerPayload;
import pl.hyorinmaru.driver.repository.AnswerRepo;
import pl.hyorinmaru.driver.service.AnswerService;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepo repository;

    @Override
    @Transactional
    public void create(Question question, List<AnswerPayload> answerPayloadList) {

        List<Answer> answers = answerPayloadList
                .stream()
                .map(answer -> AnswerFromRequest(question, answer))
                .toList();

        repository.saveAll(answers);
    }

    @Override
    public Answer readById(Long answerId) {
        return repository.findById(answerId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Answer with %s id was not found", answerId))
        );
    }

    @Override
    public List<Answer> readAll() {
        return repository.findAll();
    }

    @Override
    public List<Answer> readAllByQuestionId(Long questionId) {
        return repository.findAllByQuestionId(questionId);
    }

    @Override
    public Answer update(AnswerPayload answerPayload, Long id) {
        Answer answer = readById(id);
        answer.setContent(answerPayload.getContent());
        answer.setCorrect(answerPayload.isCorrect());
        return repository.save(answer);
    }

    @Override
    public void delete(Long id) {
        readById(id);
        repository.deleteById(id);
    }

    private Answer AnswerFromRequest(Question question, AnswerPayload answerPayload){
        return Answer
                .builder()
                .content(answerPayload.getContent())
                .isCorrect(answerPayload.isCorrect())
                .question(question)
                .build();
    }
}

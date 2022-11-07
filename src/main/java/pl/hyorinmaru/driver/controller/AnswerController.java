package pl.hyorinmaru.driver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.hyorinmaru.driver.model.Answer;
import pl.hyorinmaru.driver.payload.answer.AnswerPayload;
import pl.hyorinmaru.driver.service.AnswerService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/answers/{id}")
    public Answer getOneAnswerById(@PathVariable Long id){
        return answerService.readById(id);
    }

    @GetMapping("/answers")
    List<Answer> getAllAnswers(){
        return answerService.readAll();
    }

    @PutMapping("/answers/{id}")
    public Answer update(@RequestBody AnswerPayload answerPayload, @PathVariable Long id){
        return answerService.update(answerPayload, id);
    }

    @DeleteMapping("/answers/{id}")
    public void delete(@PathVariable Long id){
        answerService.delete(id);
    }

}

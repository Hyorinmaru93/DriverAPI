package pl.hyorinmaru.driver.payload.answer;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerPayload {

    @NotNull
    private String content;

    @NotNull
    private boolean isCorrect;
}

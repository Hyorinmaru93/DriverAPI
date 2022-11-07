package pl.hyorinmaru.driver.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @OneToMany(
            mappedBy = "question",
            fetch = FetchType.EAGER
    )
    private List<Answer> answers = new ArrayList<>();


}

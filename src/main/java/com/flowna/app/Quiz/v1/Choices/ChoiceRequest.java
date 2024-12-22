package com.flowna.app.Quiz.v1.Choices;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceRequest {
    private int id;
    private int deckId;
    private int courseId;
    private int lectureId;
    private int quizId;
    private int questionId;
    private String content;
    private boolean correct;

}

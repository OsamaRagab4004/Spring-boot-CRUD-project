package com.flowna.app.Quiz.v1.Quiz;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequest {
    private int id;
    private int deckId;
    private int courseId;
    private int lectureId;
    private String quizTyp;
    private String title;
}

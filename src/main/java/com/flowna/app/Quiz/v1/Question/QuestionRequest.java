package com.flowna.app.Quiz.v1.Question;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    private int id;
    private int deckId;
    private int courseId;
    private int lectureId;
    private int quizId;
    private String question;
    private String answer;
    private String typ;
    private String badge;
    private int correctTimes;
    private int wrongTimes;
    private int aiPredictionDifficulty;
    private String pdfReference;

}

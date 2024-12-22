package com.flowna.app.ReviewSession.v1.ReviewSessionFlashcards;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSessionFlashcardsRequest {
    private int id;
    private int flashcardId;
    private int reviewSessionId;
    private int difficulty;

}

package com.flowna.app.Flashcard.v1;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashcardRequest {
    private int id;
    private int deckId;
    private int lectureId;
    private int courseId;
    private int flashcardsGroupId;
    private String question;
    private String answer;
    private boolean isFavourite;
    private int difficultyLevel;
    private String pdfReference;
    @NotNull(message = "Value cannot be null")
    private Integer seenTimes;
}

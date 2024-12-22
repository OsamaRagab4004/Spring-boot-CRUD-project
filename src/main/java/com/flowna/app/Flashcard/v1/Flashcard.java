package com.flowna.app.Flashcard.v1;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowna.app.FlashcardsGroup.v1.FlashcardsGroup;
import com.flowna.app.ReviewSession.v1.ReviewSessionFlashcards.ReviewSessionFlashcards;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flashcard")
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String question;
    @Column
    private String answer;
    @Column
    private boolean isFavourite;
    @Column
    private String pdfReference;
    @CreationTimestamp
    @Column
    private LocalDateTime lastReviewed;
    @Column
    private int difficulty;
    @Column
    private boolean hasReviewSession;
    @Column
    private String badge;
    @Column
    private int aiPredictionDifficulty;






    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="flashcards_group_id")
    FlashcardsGroup flashcardsGroup;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "flashcard")
    ReviewSessionFlashcards reviewSessionFlashcardsSet;


    @PrePersist
    public void defaultValues() {
        this.hasReviewSession=false;
        this.isFavourite = false;
    }




}

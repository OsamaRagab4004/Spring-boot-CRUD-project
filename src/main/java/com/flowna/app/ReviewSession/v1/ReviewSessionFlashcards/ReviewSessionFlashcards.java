package com.flowna.app.ReviewSession.v1.ReviewSessionFlashcards;


import com.flowna.app.Flashcard.v1.Flashcard;
import com.flowna.app.FlashcardsGroup.v1.FlashcardsGroup;
import com.flowna.app.ReviewSession.v1.ReviewSession;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review_session_flashcards")
public class ReviewSessionFlashcards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private int difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_session_id")
    ReviewSession reviewSession;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_id")
    Flashcard flashcard;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcards_group_id")
    FlashcardsGroup flashcardsGroup;




    @PrePersist
    public void atCreate(){
        this.difficulty = 0;
    }





}

package com.flowna.app.ReviewSession.v1.ReviewSessionFlashcards;

import com.flowna.app.Flashcard.v1.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReviewSessionFlashcardsRepository extends JpaRepository<ReviewSessionFlashcards, Integer> {
    @Query("SELECT rsf.flashcard FROM ReviewSessionFlashcards rsf WHERE rsf.reviewSession.id = :sessionId")
    List<Flashcard> findFlashcardsByReviewSessionId(@Param("sessionId") Long sessionId);

    @Query("SELECT rsf FROM ReviewSessionFlashcards rsf WHERE rsf.flashcard.id = :flashcardId")
    Optional<ReviewSessionFlashcards> findByFlashcardId(@Param("flashcardId") int flashcardId);


}

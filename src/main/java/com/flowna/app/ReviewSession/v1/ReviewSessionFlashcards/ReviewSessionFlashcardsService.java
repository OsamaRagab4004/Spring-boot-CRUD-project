package com.flowna.app.ReviewSession.v1.ReviewSessionFlashcards;

import com.flowna.app.Flashcard.v1.Flashcard;
import com.flowna.app.Flashcard.v1.FlashcardRepository;
import com.flowna.app.ReviewSession.v1.ReviewSession;
import com.flowna.app.ReviewSession.v1.ReviewSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewSessionFlashcardsService {
    final
    ReviewSessionFlashcardsRepository reviewSessionFlashcardsRepository;
    final FlashcardRepository flashcardRepository;
    final ReviewSessionRepository reviewSessionRepository;

    public ReviewSessionFlashcardsService(ReviewSessionFlashcardsRepository reviewSessionFlashcardsRepository,
                                          FlashcardRepository flashcardRepository, ReviewSessionRepository reviewSessionRepository) {
        this.reviewSessionFlashcardsRepository = reviewSessionFlashcardsRepository;
        this.flashcardRepository = flashcardRepository;
        this.reviewSessionRepository = reviewSessionRepository;
    }


    public List<Flashcard> getFlashcardsForReviewSession(long id) {
        return reviewSessionFlashcardsRepository.findFlashcardsByReviewSessionId(id);

    }

    public ReviewSessionFlashcards create(ReviewSessionFlashcardsRequest reviewSessionFlashcardsRequest) {
        Flashcard flashcard = flashcardRepository.findById(reviewSessionFlashcardsRequest.getFlashcardId()).orElseThrow(
                ()-> new IllegalStateException("Flashcard not found")
        );
        ReviewSession reviewSession = reviewSessionRepository.findById(reviewSessionFlashcardsRequest.getReviewSessionId()).orElseThrow(
                ()-> new IllegalStateException("ReviewSession is not found")
        );

        ReviewSessionFlashcards reviewSessionFlashcards = ReviewSessionFlashcards.builder()
                .difficulty(reviewSessionFlashcardsRequest.getDifficulty())
                .flashcard(flashcard)
                .reviewSession(reviewSession)
                .build();
        reviewSessionFlashcardsRepository.save(reviewSessionFlashcards);
        return reviewSessionFlashcards;
    }

    public ReviewSessionFlashcards update(int flashcardId, int difficulty) {
        ReviewSessionFlashcards reviewSessionFlashcards =
                reviewSessionFlashcardsRepository.findByFlashcardId(flashcardId).orElseThrow(
                        ()-> new IllegalStateException("reviewSessionFlashcards not found!")
                );
        reviewSessionFlashcards.setDifficulty(difficulty);
        reviewSessionFlashcardsRepository.save(reviewSessionFlashcards);
        return reviewSessionFlashcards;
    }

}

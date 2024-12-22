package com.flowna.app.Flashcard.v1;

import com.flowna.app.FlashcardsGroup.v1.FlashcardsGroup;
import com.flowna.app.FlashcardsGroup.v1.FlashcardsGroupRespository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FlashcardService {
final FlashcardRepository flashcardRepository;
final FlashcardsGroupRespository flashcardsGroupRespository;
public FlashcardService(FlashcardRepository flashcardRepository, FlashcardsGroupRespository flashcardsGroupRespository) {
    this.flashcardRepository = flashcardRepository;
    this.flashcardsGroupRespository = flashcardsGroupRespository;
}

    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #flashcardRequest.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#flashcardRequest.deckId,#flashcardRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#flashcardRequest.courseId, #flashcardRequest.lectureId)" +
            "&& @CheckOwner.isFlashcardsGroupOwnedByLecture(#flashcardRequest.lectureId, #flashcardRequest.flashcardsGroupId)")
    public Flashcard create(FlashcardRequest flashcardRequest, int userId) {
        Optional<FlashcardsGroup> flashcardsGroupOptional =
                flashcardsGroupRespository.findById(flashcardRequest.getFlashcardsGroupId());
        if(flashcardsGroupOptional.isEmpty()) throw new IllegalStateException("flashcardsGroup is not found !");
        FlashcardsGroup flashcardsGroup = flashcardsGroupOptional.get();
        Flashcard flashcard = Flashcard.builder()
                .question(flashcardRequest.getQuestion())
                .answer(flashcardRequest.getAnswer())
                .pdfReference(flashcardRequest.getPdfReference())
                .flashcardsGroup(flashcardsGroup)
                .build();
        flashcardRepository.save(flashcard);
        return flashcard;

    }


    /**
     * This function should be implemented for array of ids of flashcards on the server to guarantee all ids are updated
     **/
    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #flashcardRequest.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#flashcardRequest.deckId,#flashcardRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#flashcardRequest.courseId, #flashcardRequest.lectureId)" +
            "&& @CheckOwner.isFlashcardsGroupOwnedByLecture(#flashcardRequest.lectureId, #flashcardRequest.flashcardsGroupId)"+
            "&& @CheckOwner.isFlashcardOwnedByGroup(#flashcardRequest.flashcardsGroupId,#flashcardRequest.id)")
    public Flashcard update(FlashcardRequest flashcardRequest, int userId){
           Optional<Flashcard> flashcardOptional = flashcardRepository.findById(flashcardRequest.getId());
           if(flashcardOptional.isEmpty()) throw new IllegalStateException("Flashcard is not found!");
           Flashcard  flashcard = flashcardOptional.get();
           flashcard.setLastReviewed(LocalDateTime.now());
           flashcardRepository.save(flashcard);
           return flashcard;
    }



}

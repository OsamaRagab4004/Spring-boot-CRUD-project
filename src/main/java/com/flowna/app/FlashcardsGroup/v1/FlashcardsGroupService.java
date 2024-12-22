package com.flowna.app.FlashcardsGroup.v1;

import com.flowna.app.Flashcard.v1.Flashcard;
import com.flowna.app.Flashcard.v1.FlashcardRepository;
import com.flowna.app.lecture.v1.Lecture;
import com.flowna.app.lecture.v1.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

@Service
public class FlashcardsGroupService {
    final
    FlashcardsGroupRespository flashcardsGroupRespository;
    final LectureRepository lectureRepository;
    final FlashcardRepository flashcardRepository;

    public FlashcardsGroupService(FlashcardsGroupRespository flashcardsGroupRespository, LectureRepository lectureRepository,
                                  FlashcardRepository flashcardRepository) {
        this.flashcardsGroupRespository = flashcardsGroupRespository;
        this.lectureRepository = lectureRepository;
        this.flashcardRepository = flashcardRepository;
    }

    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #flashcardsGroupRequest.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#flashcardsGroupRequest.deckId,#flashcardsGroupRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#flashcardsGroupRequest.courseId, #flashcardsGroupRequest.lectureId)")
    public FlashcardsGroup create(FlashcardsGroupRequest flashcardsGroupRequest, int userId) {
        Lecture lecture = lectureRepository.findById(flashcardsGroupRequest.getLectureId()).get();
        FlashcardsGroup flashcardsGroup = FlashcardsGroup.builder()
                .title(flashcardsGroupRequest.getTitle())
                .tableContent(flashcardsGroupRequest.getTableContent())
                .lecture(lecture)
                .build();
        flashcardsGroupRespository.save(flashcardsGroup);
        return flashcardsGroup;

    }



    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #flashcardsGroupRequest.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#flashcardsGroupRequest.deckId,#flashcardsGroupRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#flashcardsGroupRequest.courseId, #flashcardsGroupRequest.lectureId)" +
            "&& @CheckOwner.isFlashcardsGroupOwnedByLecture(#flashcardsGroupRequest.lectureId, #flashcardsGroupRequest.id)")
    public FlashcardsGroup update(FlashcardsGroupRequest flashcardsGroupRequest, int userId) {
        FlashcardsGroup flashcardsGroup = FlashcardsGroup.builder()
                .title(flashcardsGroupRequest.getTitle())
                .build();
        flashcardsGroupRespository.save(flashcardsGroup);
        return flashcardsGroup;
    }


    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#deckId,#courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#courseId, #lectureId)" +
            "&& @CheckOwner.isFlashcardsGroupOwnedByLecture(#lectureId, #groupId)")
    public Set<Flashcard> getAllFlashcards(int userId, int deckId, int courseId, int lectureId, int groupId) {
        Set<Flashcard> flashcardSet = flashcardRepository.getFlashcardsByFlashcardsGroupId(groupId);
        return flashcardSet;
    }

}

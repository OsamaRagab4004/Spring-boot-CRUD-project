package com.flowna.app.Flashcard.v1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
    Set<Flashcard> getFlashcardsByFlashcardsGroupId(int id);
}

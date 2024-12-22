package com.flowna.app.Flashcard.v1;


import com.flowna.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/flashcards/flashcard")
@RequiredArgsConstructor
public class FlashcardController {
    FlashcardService flashcardService;

    @Autowired
    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
        @RequestBody FlashcardRequest flashcardRequest,
        @AuthenticationPrincipal User userDetails
    ) {
        Flashcard flashcard = flashcardService.create(flashcardRequest, userDetails.getId());
        return ResponseEntity.ok(flashcard);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestBody FlashcardRequest flashcardRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        Flashcard flashcard = flashcardService.update(flashcardRequest, userDetails.getId());
        return ResponseEntity.ok(flashcard);
    }




}

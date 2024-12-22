package com.flowna.app.FlashcardsGroup.v1;


import com.flowna.app.Flashcard.v1.Flashcard;
import com.flowna.app.user.User;
import com.sun.mail.iap.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/lecture/flashcards")
@RequiredArgsConstructor

public class FlashcardsGroupController {
    FlashcardsGroupService flashcardsGroupService;
    public FlashcardsGroupController(FlashcardsGroupService flashcardsGroupService) {
        this.flashcardsGroupService = flashcardsGroupService;

    }

    @PostMapping("/create")
    public ResponseEntity<?> create (
        @RequestBody FlashcardsGroupRequest flashcardsGroupRequest,
        @AuthenticationPrincipal User userDetails
    ) {
        FlashcardsGroup flashcardsGroup = flashcardsGroupService.create(flashcardsGroupRequest, userDetails.getId());
        return ResponseEntity.ok(flashcardsGroup);
    }


    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestBody FlashcardsGroupRequest flashcardsGroupRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        FlashcardsGroup flashcardsGroup = flashcardsGroupService.update(flashcardsGroupRequest, userDetails.getId());
        return ResponseEntity.ok(flashcardsGroup);
    }


    @GetMapping("/{deckId}/{courseId}/{lectureId}/{groupId}")
    public ResponseEntity<?> getAllFlashcards(
            @PathVariable int deckId,
            @PathVariable int courseId,
            @PathVariable int lectureId,
            @PathVariable int groupId,
            @AuthenticationPrincipal User userDetails
    ) {
        Set<Flashcard> flashcardSet =
                flashcardsGroupService.getAllFlashcards(userDetails.getId(), deckId,courseId,lectureId,groupId);
        return ResponseEntity.ok(flashcardSet);
    }




}

package com.flowna.app.deck.v1;
import com.flowna.app.Course.v1.Course;
import com.flowna.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/deck")
@RequiredArgsConstructor
public class DeckController {

    DeckService deckService;

    @Autowired
    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping("/decks")
    public ResponseEntity<List<Deck>> getDecks(
            @AuthenticationPrincipal User userDetails
    ) {
        List<Deck> decks = deckService.getDecksByUserId(userDetails.getId());

        // Check if the deck list is empty
        if (decks == null || decks.isEmpty()) {
            return ResponseEntity.noContent().build();  // 204 No Content
        }

        return ResponseEntity.ok(decks);  // 200 OK with the list of decks
    }

    @PostMapping("/create")
    public ResponseEntity<?> generateDeck(
            @RequestBody DeckRequest deckRequest,
            @AuthenticationPrincipal User userDetails
    ) {
       Deck generatedDeck =  deckService.generateDeck(deckRequest, userDetails.getId());
        return ResponseEntity.ok(generatedDeck);

    }


    @PostMapping("/update")
    public ResponseEntity<?> updateDeck(
            @RequestBody DeckRequest deckRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        Deck updatedDeck  = deckService.updateDeck(userDetails.getId(), deckRequest);
        return ResponseEntity.ok(updatedDeck);

    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<?> getCoursesForDeckById(
            @PathVariable int id,
            @AuthenticationPrincipal User userDetails
            ) {
        Set<Course> courses = deckService.getCoursesForDeckById(id,userDetails.getId());
        return ResponseEntity.ok(courses);
    }

}

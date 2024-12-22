package com.flowna.app.deck.v1;
import com.flowna.app.Course.v1.Course;
import com.flowna.app.user.User;
import com.flowna.app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeckService {

    DeckRepository deckRepository;
    UserRepository userRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository, UserRepository userRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
    }

    public List<Deck> getDecksByUserId(int id) {
        List<Deck> decks = deckRepository.getDecksByUserId(id);
        if(decks.isEmpty()) throw new IllegalStateException("There is no decks");
        return decks;
    }

    public Deck generateDeck(DeckRequest deckRequest, int userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("User not found")
                );

        List<Deck> userDecks = deckRepository.getDecksByUserId(userId);
        if(!userDecks.isEmpty()) {
            userDecks.stream()
                    .filter(deck -> deck.getName().equals(deckRequest.getName()) )
                    .forEach(deck-> {
                        throw new IllegalStateException("Found the deck with the name");
                    });

        }

        Deck createdDeck = Deck.builder()
                .name(deckRequest.getName())
                .user(user)
                .build();
        deckRepository.save(createdDeck);
        return createdDeck;
    }


    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId,#deckRequest.id)")
    public Deck updateDeck(int userId,DeckRequest deckRequest) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );

        List<Deck> userDecks = getDecksByUserId(userId);
        Optional<Deck> userOwnDeck= userDecks.stream().filter(
                deck -> deck.getId().equals(deckRequest.getId())
        ).findFirst();
        //if(userOwnDeck.isEmpty()) throw new IllegalStateException("Deck deos not belong to the user");
        // check if name is unique
        userDecks.stream()
                .filter(deck -> deck.getName().equals(deckRequest.getName()) )
                .forEach(deck-> {
                    throw new IllegalStateException("Found the deck with the name");
                });


        Deck updatedDeck = deckRepository.findDeckById(deckRequest.getId()).orElseThrow(
                () -> new NoSuchElementException("deck not found")
        );
        updatedDeck.setName(deckRequest.getName());
        deckRepository.save(updatedDeck);
        return updatedDeck;
    }

    public void removeDeck(DeckRequest deckRequest) {}

    public Set<Course> getCoursesForDeckById(int DeckId, int userId) {
        Optional<Deck> deckOptional = deckRepository.findDeckById(DeckId);
        if(deckOptional.isPresent()) {

            Optional<User> ownerOfDeck = userRepository.findById(userId);
            if(ownerOfDeck.isPresent()) {
                List<Deck> userDecks = ownerOfDeck.get().getDecks();
                Optional<Deck> matchingDeck = userDecks.stream().filter(deck -> deck.getId().equals(DeckId)).findFirst();
                if(matchingDeck.isPresent()) {
                    return deckOptional.get().getCourses();
                } else {
                    throw new IllegalStateException("Deck not belongs to this user !!");
                }
            }

        } else {
            throw new IllegalStateException("Deck not found !");
        }
    return null;
    }




}

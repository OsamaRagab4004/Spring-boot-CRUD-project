package com.flowna.app.deck.v1;

import com.flowna.app.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends JpaRepository<Deck, Integer> {

    List<Deck> getDecksByUserId(int id);
    Optional<Deck> findDeckByName(String name);
    Optional<Deck> findDeckById(int id);


}

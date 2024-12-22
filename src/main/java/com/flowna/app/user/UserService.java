package com.flowna.app.user;

import com.flowna.app.auth.RegisterRequest;
import com.flowna.app.deck.v1.Deck;
import com.flowna.app.deck.v1.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final DeckRepository deckRepository;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }


    public ResponseEntity<?> updateUser(RegisterRequest registerRequest) {
        System.out.println(registerRequest);
        Optional<User> user = repository.findByEmail(registerRequest.getEmail());
        var updatedUser = user.orElseThrow();
        if (registerRequest.getFirstname() != null) updatedUser.setFirstname(registerRequest.getFirstname());
        if (registerRequest.getLastname() != null) updatedUser.setLastname(registerRequest.getLastname());
        if (registerRequest.getPassword() != null) {
            updatedUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        } else {

        }

        if (registerRequest.getFirstname() == null && registerRequest.getLastname() == null && registerRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed to update user !");
        } else {
            repository.save(updatedUser);
            return ResponseEntity.ok("User is updated !");
        }
    }

    public UserPreferences setUserPreferences(int id, UserPreferencesDTO userPrefs) {
        User user = repository.findById(id).orElseThrow();
        // Check if preferences are already set
        if (user.getUserPreferences() != null) {
            throw new IllegalStateException("UserPreferences already set for the user");
        }
        UserPreferences prefs = UserPreferences.builder()
                .learning_type(userPrefs.getLearning_type())
                .complexity_level(userPrefs.getComplexity_level())
                .focus_areas(userPrefs.getFocus_areas())
                .explanation_depth(userPrefs.getExplanation_depth())
                .content_formating(userPrefs.getContent_formating())
                .email_notification(userPrefs.getEmail_notification())
                .language_prefence(userPrefs.getLanguage_prefence())
                .study_times(userPrefs.getStudy_time())
                .study_days(userPrefs.getStudy_days())
                .user(user)
                .build();

        user.setUserPreferences(prefs);
        repository.save(user);
        return prefs;

    }

    public UserPreferences updateUserPreferences(int id, UserPreferencesDTO updatedPrefs) {
        User user = repository.findById(id).orElseThrow();

        UserPreferences existingPrefs = user.getUserPreferences();
        if (existingPrefs == null) {
            throw new IllegalStateException("The user doesn't have any prefernces");
        }
        if (updatedPrefs.getLearning_type() != null) existingPrefs.setLearning_type(updatedPrefs.getLearning_type());
        if (updatedPrefs.getComplexity_level() != null)
            existingPrefs.setComplexity_level(updatedPrefs.getComplexity_level());
        if (updatedPrefs.getFocus_areas() != null) existingPrefs.setFocus_areas(updatedPrefs.getFocus_areas());
        if (updatedPrefs.getExplanation_depth() != null)
            existingPrefs.setExplanation_depth(updatedPrefs.getExplanation_depth());
        if (updatedPrefs.getContent_formating() != null)
            existingPrefs.setContent_formating(updatedPrefs.getContent_formating());
        if (updatedPrefs.getEmail_notification() != null)
            existingPrefs.setEmail_notification(updatedPrefs.getEmail_notification());
        if (updatedPrefs.getLanguage_prefence() != null)
            existingPrefs.setLanguage_prefence(updatedPrefs.getLanguage_prefence());
        if (updatedPrefs.getStudy_days() != null) existingPrefs.setStudy_days(updatedPrefs.getStudy_days());
        if (updatedPrefs.getStudy_time() != null) existingPrefs.setStudy_times(updatedPrefs.getStudy_time());
        user.setUserPreferences(existingPrefs);
        repository.save(user);
        return user.getUserPreferences();
    }

    public UserPreferences getUserPreferencesById(int id) {
        User user = repository.findById(id).orElseThrow();
        return user.getUserPreferences();
    }

    public User getUserById(int id) {
        User user = repository.findById(id).orElseThrow();
        return user;
    }


    /**
     * Refactor this function in common/services/checkOwner
     * */
    public boolean ownDeck(int userId, int deckId) {
        List<Deck> userDecks = deckRepository.getDecksByUserId(userId);
        Optional<Deck> matchingDeck = userDecks.stream().filter(
                deck -> deck.getId().equals(deckId)
        ).findFirst();
        if(matchingDeck.isPresent()) return true;
    return false;
    }



}

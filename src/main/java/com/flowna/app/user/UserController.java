package com.flowna.app.user;

import com.flowna.app.auth.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;


    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-user")
    public ResponseEntity<?> updateUser(
            @Valid @RequestBody RegisterRequest registerRequest) {
        return service.updateUser(registerRequest);
    }

    @PostMapping("/set-prefernces")
    public ResponseEntity<?> setPrefernces(
           @RequestBody UserPreferencesDTO prefs, @AuthenticationPrincipal User userDetails) {
        service.setUserPreferences(userDetails.getId(), prefs);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/update-prefernces")
    public ResponseEntity<?> updatePrefernces(
            @RequestBody UserPreferencesDTO prefs , @AuthenticationPrincipal User userDetails) {
        service.updateUserPreferences(userDetails.getId(),prefs);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-preferences")
    public ResponseEntity<?> getPrefernces(
            @AuthenticationPrincipal User userDetails
    ) {
        return ResponseEntity.ok(service.getUserPreferencesById(userDetails.getId()));
    }




}


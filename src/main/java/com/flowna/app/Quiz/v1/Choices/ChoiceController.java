package com.flowna.app.Quiz.v1.Choices;


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
@RequestMapping("/api/v1/lecture/quiz/choices")
@RequiredArgsConstructor
public class ChoiceController {
    ChoiceService choiceService;

    @Autowired
    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody ChoiceRequest choiceRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        Choice choice = choiceService.create(choiceRequest, userDetails.getId());
        return ResponseEntity.ok(choice);
    }


    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestBody ChoiceRequest choiceRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        Choice choice = choiceService.update(choiceRequest, userDetails.getId());
        return ResponseEntity.ok(choice);
    }



}

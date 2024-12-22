package com.flowna.app.ScrumBoard.ScrumboardCard;


import com.flowna.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/scrumboard/card")
@RequiredArgsConstructor
public class ScrumboardCardController {
    ScrumboardCardService scrumboardCardService;

    @Autowired
    public ScrumboardCardController(ScrumboardCardService scrumboardCardService) {
        this.scrumboardCardService = scrumboardCardService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
        @AuthenticationPrincipal User userDetails,
        @RequestBody ScrumboardCardRequest request
    ) {
        return ResponseEntity.ok(scrumboardCardService.create(userDetails.getId(), request));
    }







}

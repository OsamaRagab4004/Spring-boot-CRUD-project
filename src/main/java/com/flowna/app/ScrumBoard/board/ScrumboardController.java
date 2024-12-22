package com.flowna.app.ScrumBoard.board;


import com.flowna.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/scrumboard")
@RequiredArgsConstructor
public class ScrumboardController {
    ScrumboardService scrumboardService;

    @Autowired
    public ScrumboardController(ScrumboardService scrumboardService) {
        this.scrumboardService = scrumboardService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createScrumboard(
            @RequestBody ScrumboardRequest request,
            @AuthenticationPrincipal User userDetails) {
        Scrumboard scrumboard =  scrumboardService.create(userDetails.getId(), request);
        return ResponseEntity.ok(scrumboard);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getScrumboard(
            @PathVariable int boardId,
            @AuthenticationPrincipal User userDetails) {
        ScrumboardDTO scrumboard = scrumboardService.getScrumboardById(userDetails.getId(), boardId);
        return ResponseEntity.ok(scrumboard);
    }

    @GetMapping("/boards")
    public ResponseEntity<?> getAllScrumboards(
            @AuthenticationPrincipal User userDetails) {
        Set<ScrumboardDTO> scrumboards = scrumboardService.getBoardsByUserId(userDetails.getId());
        return ResponseEntity.ok(scrumboards);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(
        @RequestBody ScrumboardRequest request,
        @AuthenticationPrincipal User userDetails
    ) {
        return ResponseEntity.ok(scrumboardService.update(userDetails.getId(), request));
    }






}

package com.flowna.app.ScrumBoard.ScrumboardList;


import com.flowna.app.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/scrumboard/lists")
@RequiredArgsConstructor
public class ScrumboardListController {

    ScrumboardListService  scrumboardListService;

    @Autowired
    public ScrumboardListController(ScrumboardListService scrumboardListService) {
        this.scrumboardListService = scrumboardListService;
    }


    @PostMapping("/create")
    public ResponseEntity<ScrumboardListDTO2> create(
            @AuthenticationPrincipal User userDetails,
            @RequestBody ScrumboardListRequest request

    ) {
        return ResponseEntity.ok(scrumboardListService.create(userDetails.getId(), request));
    }


    @GetMapping("/{boardId}")
    public ResponseEntity<Set<ScrumboardListDTO2>> getLists(
            @AuthenticationPrincipal User userDetails,
            @PathVariable int boardId
    ) {
        return ResponseEntity.ok(scrumboardListService.getLists(userDetails.getId(), boardId));
    }

    @GetMapping("/{boardId}/{listId}")
    public ResponseEntity<ScrumboardListDTO2> getListById(
            @AuthenticationPrincipal User userDetails,
            @PathVariable int boardId,
            @PathVariable int listId
    ) {
        return ResponseEntity.ok(scrumboardListService.getListById(userDetails.getId(), boardId, listId));

    }



    @PutMapping("/update")
    public ResponseEntity<ScrumboardListDTO2> update(
            @AuthenticationPrincipal User userDetails,
            @RequestBody ScrumboardListRequest request
    ) {
        return ResponseEntity.ok(scrumboardListService.update(userDetails.getId(), request));
    }

    @DeleteMapping("/{boardId}/{listId}")
    public ResponseEntity<Void> deleteListById(
            @AuthenticationPrincipal User userDetails,
            @PathVariable int boardId,
            @PathVariable int listId
    ) {
        scrumboardListService.deleteListById(listId, userDetails.getId(), boardId);
        return ResponseEntity.ok().build();
    }

}

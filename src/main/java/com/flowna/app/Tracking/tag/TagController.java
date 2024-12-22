package com.flowna.app.Tracking.tag;


import com.flowna.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/tracking/tag")
@RequiredArgsConstructor
public class TagController {
    TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody TagRequest tagRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        return ResponseEntity.ok(tagService.create(tagRequest, userDetails.getId()));

    }

    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestBody TagRequest tagRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        return ResponseEntity.ok(tagService.update(userDetails.getId(), tagRequest));
    }

    @GetMapping("/tags")
    public ResponseEntity<?> getTags(
            @AuthenticationPrincipal User userDetails
    ) {
        return ResponseEntity.ok(tagService.getTags(userDetails.getId()));
    }



}

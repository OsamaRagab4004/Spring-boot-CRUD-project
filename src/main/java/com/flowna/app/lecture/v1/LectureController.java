package com.flowna.app.lecture.v1;


import com.flowna.app.page.v1.Page;
import com.flowna.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/lecture")
@RequiredArgsConstructor

public class LectureController {
    LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody LectureRequest lectureRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        Lecture createdLecture = lectureService.createLecture(lectureRequest, userDetails.getId());
        return ResponseEntity.ok(createdLecture);
    }


    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestBody LectureRequest lectureRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        Lecture createdLecture = lectureService.updateLecture(lectureRequest, userDetails.getId());
        return ResponseEntity.ok(createdLecture);
    }

    @GetMapping("/pages/{deckId}/{courseId}/{lectureId}")
    public ResponseEntity<?> getAllPages(
            @PathVariable int deckId,
            @PathVariable int courseId,
            @PathVariable int lectureId,
            @AuthenticationPrincipal User userDetails
    ) {
        Set<Page> pages = lectureService.getAllPagesForLecture(deckId, courseId,lectureId, userDetails.getId());
        return ResponseEntity.ok(pages);
    }


}

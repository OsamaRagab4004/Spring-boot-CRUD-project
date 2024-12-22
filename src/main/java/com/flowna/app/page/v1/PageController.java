package com.flowna.app.page.v1;


import com.flowna.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lecture/page")
@RequiredArgsConstructor
public class PageController {
    PageService pageService;

    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody PageRequest pageRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        Page page = pageService.create(pageRequest, userDetails.getId());
        return ResponseEntity.ok(page);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestBody PageRequest pageRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        Page page = pageService.update(pageRequest, userDetails.getId());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/pages/{deckId}/{courseId}/{lectureId}")
    public ResponseEntity<?> get(
            @PathVariable int deckId,
            @PathVariable int courseId,
            @PathVariable int lectureId,
            @PathVariable int pageId,
            @AuthenticationPrincipal User userDetails
    ){

        Page page = pageService.get(deckId, courseId,lectureId,pageId,userDetails.getId());
        return ResponseEntity.ok(page);
    }





}

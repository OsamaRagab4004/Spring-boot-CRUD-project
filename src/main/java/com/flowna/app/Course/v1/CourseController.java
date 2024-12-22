package com.flowna.app.Course.v1;

import com.flowna.app.lecture.v1.Lecture;
import com.flowna.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCourse(
            @RequestBody CourseRequest courseRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        courseService.createCourse(courseRequest,userDetails.getId());
        return ResponseEntity.ok().body("Course is created");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCourse(
            @RequestBody CourseRequest courseRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        courseService.updateCourse(courseRequest, userDetails.getId());
        return ResponseEntity.ok().body("Course is updates !");
    }

    @GetMapping("/course/{deckId}/{courseId}/lectures")
    public  ResponseEntity<?> getLectures(
            @PathVariable int deckId,
            @PathVariable int courseId,
            @AuthenticationPrincipal User userDetails
    ){
        Set<Lecture> lectures = courseService.getLecturesByCourseId(userDetails.getId(),courseId,deckId);
        return ResponseEntity.ok(lectures);
    }


}

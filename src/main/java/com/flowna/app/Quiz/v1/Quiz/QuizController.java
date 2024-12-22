package com.flowna.app.Quiz.v1.Quiz;


import com.flowna.app.Quiz.v1.Question.Question;
import com.flowna.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/lecture/quiz")
@RequiredArgsConstructor
public class QuizController {
    QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
        @RequestBody QuizRequest quizRequest,
        @AuthenticationPrincipal User userDetails
    ) {
        Quiz createdQuiz =  quizService.create(quizRequest, userDetails.getId());
        return ResponseEntity.ok(createdQuiz);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(
        @RequestBody QuizRequest quizRequest,
        @AuthenticationPrincipal User userDetails
    ) {
        Quiz updatedQuiz = quizService.update(quizRequest, userDetails.getId());
        return ResponseEntity.ok(updatedQuiz);
    }

    @GetMapping("/{deckId}/{courseId}/{lectureId}/{quizId}")
    public ResponseEntity<?> getAllQuestions(
            @PathVariable int deckId,
            @PathVariable int courseId,
            @PathVariable int lectureId,
            @PathVariable int quizId,
            @AuthenticationPrincipal User userDetails
    ) {
        Set<Question> questions = quizService.getAllQuestions(userDetails.getId(),deckId,courseId, lectureId, quizId);
        return ResponseEntity.ok(questions);
    }


}

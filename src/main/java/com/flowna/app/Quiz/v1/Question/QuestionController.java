package com.flowna.app.Quiz.v1.Question;


import com.flowna.app.Quiz.v1.Quiz.QuizRequest;
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
@RequestMapping("/api/v1/lecture/quiz/question")
@RequiredArgsConstructor
public class QuestionController {
    QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody QuestionRequest questionRequest,
            @AuthenticationPrincipal User userDetails
            ) {
        Question question = questionService.create(questionRequest, userDetails.getId());
        return ResponseEntity.ok(question);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestBody QuestionRequest questionRequest,
            @AuthenticationPrincipal User userDetails
    ) {
        Question question = questionService.update(questionRequest, userDetails.getId());
        return ResponseEntity.ok(question);
    }


}

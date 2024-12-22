package com.flowna.app.Quiz.v1.Question;

import com.flowna.app.Quiz.v1.Quiz.Quiz;
import com.flowna.app.Quiz.v1.Quiz.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    final
    QuestionRepository questionRepository;
    final
    QuizRepository quizRepository;

    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }

    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #questionRequest.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#questionRequest.deckId,#questionRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#questionRequest.courseId, #questionRequest.lectureId)" +
            "&& @CheckOwner.isQuizOwnedByLecture(#questionRequest.lectureId,#questionRequest.quizId)")
    public Question create(QuestionRequest questionRequest, int userId) {
        Quiz quiz = quizRepository.findById(questionRequest.getQuizId()).orElseThrow();
        Question question = Question.builder()
                .question(questionRequest.getQuestion())
                .answer(questionRequest.getAnswer())
                .type(questionRequest.getTyp())
                .badge(questionRequest.getBadge())
                .aiPredictionDifficulty(questionRequest.getAiPredictionDifficulty())
                .pdfReference(questionRequest.getPdfReference())
                .quiz(quiz)
                .build();
        questionRepository.save(question);
        return question;
    }


    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #questionRequest.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#questionRequest.deckId,#questionRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#questionRequest.courseId, #questionRequest.lectureId)" +
            "&& @CheckOwner.isQuizOwnedByLecture(#questionRequest.lectureId,#questionRequest.quizId)")
    public Question update(QuestionRequest questionRequest, int userId) {
        Question updatedQuestion = questionRepository.findById(questionRequest.getId()).orElseThrow();
        updatedQuestion.setQuestion(questionRequest.getQuestion());
        updatedQuestion.setAnswer(questionRequest.getAnswer());
        return updatedQuestion;
    }




}

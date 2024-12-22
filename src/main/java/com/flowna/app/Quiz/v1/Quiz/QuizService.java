package com.flowna.app.Quiz.v1.Quiz;


import com.flowna.app.Quiz.v1.Question.Question;
import com.flowna.app.Quiz.v1.Question.QuestionRepository;
import com.flowna.app.lecture.v1.Lecture;
import com.flowna.app.lecture.v1.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class QuizService {
    final
    LectureRepository lectureRepository;
    final QuizRepository quizRepository;
    final QuestionRepository questionRepository;

    public QuizService(LectureRepository lectureRepository,QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.lectureRepository = lectureRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }


    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #quizRequest.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#quizRequest.deckId,#quizRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#quizRequest.courseId, #quizRequest.lectureId)")
    public Quiz create(QuizRequest quizRequest, int userId) {
        Lecture lecture = lectureRepository.findById(quizRequest.getLectureId()).orElseThrow();
        Quiz quiz = Quiz.builder()
                .title(quizRequest.getTitle())
                .quizType(quizRequest.getQuizTyp())
                .lecture(lecture)
                .build();
        quizRepository.save(quiz);
        return quiz;
    }



    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #quizRequest.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#quizRequest.deckId,#quizRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#quizRequest.courseId, #quizRequest.lectureId)" +
            "&& @CheckOwner.isQuizOwnedByLecture(#quizRequest.lectureId,#quizRequest.id)")
    public Quiz update(QuizRequest quizRequest, int userId) {
        Optional<Quiz> quiz = quizRepository.findById(quizRequest.getId());
        if(quiz.isPresent()) {
            Quiz updatedQuiz = quiz.get();
            updatedQuiz.setTitle(quizRequest.getTitle());
            quizRepository.save(updatedQuiz);
            return updatedQuiz;
        } else {
            throw new IllegalStateException("Quiz is not found !");
        }

    }

    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#deckId,#courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#courseId, #lectureId)" +
            "&& @CheckOwner.isQuizOwnedByLecture(#lectureId,#quizId)")
    public Set<Question> getAllQuestions(int userId, int deckId, int courseId, int lectureId, int quizId) {
        Set<Question> questions = questionRepository.getQuestionsByQuizId(quizId);
        return questions;
    }

}

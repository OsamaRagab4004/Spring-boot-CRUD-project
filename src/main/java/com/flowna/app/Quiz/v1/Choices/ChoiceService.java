package com.flowna.app.Quiz.v1.Choices;

import com.flowna.app.Quiz.v1.Question.Question;
import com.flowna.app.Quiz.v1.Question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ChoiceService {

    final
    QuestionRepository questionRepository;
    final
    ChoiceRepository choiceRepository;

    public ChoiceService(QuestionRepository questionRepository, ChoiceRepository choiceRepository) {
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;

    }


    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #choiceRequest.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#choiceRequest.deckId,#choiceRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#choiceRequest.courseId, #choiceRequest.lectureId)" +
            "&& @CheckOwner.isQuizOwnedByLecture(#choiceRequest.lectureId,#choiceRequest.quizId)" +
            "&& @CheckOwner.isQuestionOwnedByQuiz(#choiceRequest.quizId, #choiceRequest.questionId)")
    public Choice create(ChoiceRequest choiceRequest, int userId) {
            Question question = questionRepository.findById(choiceRequest.getQuestionId()).orElseThrow();
            Choice choice = Choice.builder()
                    .content(choiceRequest.getContent())
                    .correct(choiceRequest.isCorrect())
                    .question(question)
                    .build();
            choiceRepository.save(choice);
            return choice;
    }


    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #choiceRequest.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#choiceRequest.deckId,#choiceRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#choiceRequest.courseId, #choiceRequest.lectureId)" +
            "&& @CheckOwner.isQuizOwnedByLecture(#choiceRequest.lectureId,#choiceRequest.quizId)" +
            "&& @CheckOwner.isQuestionOwnedByQuiz(#choiceRequest.quizId, #choiceRequest.questionId)")
    public Choice update(ChoiceRequest choiceRequest, int userId) {
        Choice choice = choiceRepository.findById(choiceRequest.getId()).orElseThrow();
        if(!choice.getContent().isEmpty()) choice.setContent(choiceRequest.getContent());
        // user should send the correct variable anyway if it's updated or not
        choice.setCorrect(choiceRequest.isCorrect());
        choiceRepository.save(choice);
        return choice;
    }



}

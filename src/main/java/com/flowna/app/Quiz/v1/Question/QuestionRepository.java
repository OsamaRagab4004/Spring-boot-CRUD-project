package com.flowna.app.Quiz.v1.Question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Set<Question> getQuestionsByQuizId(int id);

}

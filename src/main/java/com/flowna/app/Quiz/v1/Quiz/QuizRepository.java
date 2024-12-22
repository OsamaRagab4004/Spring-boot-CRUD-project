package com.flowna.app.Quiz.v1.Quiz;

import com.flowna.app.Quiz.v1.Question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}

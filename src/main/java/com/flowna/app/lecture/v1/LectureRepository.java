package com.flowna.app.lecture.v1;

import com.flowna.app.deck.v1.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
    Set<Lecture> getLecturesByCourseId(int id);

}

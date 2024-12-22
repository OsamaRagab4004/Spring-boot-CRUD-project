package com.flowna.app.LectureContent.v1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface LectureContentRepository extends JpaRepository<LectureContent, Integer> {

    Set<LectureContent> getLectureContentsByLectureId(int id);

}

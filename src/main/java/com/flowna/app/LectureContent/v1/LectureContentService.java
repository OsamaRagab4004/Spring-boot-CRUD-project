package com.flowna.app.LectureContent.v1;
import com.flowna.app.lecture.v1.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LectureContentService {


    final
    LectureContentRepository lectureContentRepository;

    @Autowired
    public LectureContentService(LectureContentRepository lectureContentRepository) {
        this.lectureContentRepository = lectureContentRepository;
    }


    public void create(int startPage, int endPage, String content, String toc) {
        if (startPage < 0 || endPage < 0 || startPage > endPage) {
            throw new IllegalArgumentException("Invalid page numbers.");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty.");
        }
        if (toc == null || toc.isEmpty()) {
            throw new IllegalArgumentException("Table of Content cannot be null or empty.");
        }
      try {
            LectureContent lectureContent = LectureContent.builder()
                    .startPage(startPage)
                    .endPage(endPage)
                    .content(content)
                    .tableOfContent(toc)
                    .build();
            lectureContentRepository.save(lectureContent);
        }  catch (DataAccessException e) {
            // Log the exception and throw a custom exception or handle it appropriately
            throw new IllegalStateException("Failed to save lecture content", e);
        }

    }

    /**
     * This function have no securing pre functions, but it's used internally in the server.
     * */
    public Set<LectureContent> getContentforlecture(int lectureId) {
        Set<LectureContent> lectureContents = lectureContentRepository.getLectureContentsByLectureId(lectureId);
        return lectureContents;
    }



}

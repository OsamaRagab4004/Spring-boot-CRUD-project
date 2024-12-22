package com.flowna.app.ReviewSession.v1.ReviewSessionSummaries;

import com.flowna.app.page.v1.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ReviewSessionSummariesRepository extends JpaRepository<ReviewSessionSummaries, Integer> {

    @Query("SELECT rsf.page FROM ReviewSessionSummaries rsf WHERE rsf.reviewSession.id = :sessionId")
    List<Page> getPagesBySessionId(@Param("sessionId")long sessionId);

    @Query("SELECT rsf FROM ReviewSessionSummaries  rsf WHERE rsf.page.id = :pageId")
    ReviewSessionSummaries getReviewSessionSummariesByPageId(@Param("pageId") int pageId);

}

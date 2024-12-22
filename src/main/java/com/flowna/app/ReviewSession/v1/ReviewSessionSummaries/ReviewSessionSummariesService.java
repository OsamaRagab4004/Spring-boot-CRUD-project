package com.flowna.app.ReviewSession.v1.ReviewSessionSummaries;
import com.flowna.app.ReviewSession.v1.ReviewSession;
import com.flowna.app.ReviewSession.v1.ReviewSessionRepository;
import com.flowna.app.page.v1.Page;
import com.flowna.app.page.v1.PageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewSessionSummariesService {

    final
    ReviewSessionSummariesRepository reviewSessionSummariesRepository;
    final PageRepository pageRepository;
    final ReviewSessionRepository reviewSessionRepository;


    public ReviewSessionSummariesService(ReviewSessionSummariesRepository reviewSessionSummariesRepository,PageRepository pageRepository, ReviewSessionRepository reviewSessionRepository) {
        this.reviewSessionSummariesRepository = reviewSessionSummariesRepository;
        this.pageRepository = pageRepository;
        this.reviewSessionRepository = reviewSessionRepository;
    }

    public List<Page> getAllPagesBySessionId(long id) {
        return reviewSessionSummariesRepository.getPagesBySessionId(id);
    }

    public ReviewSessionSummaries create(ReviewSessionSummariesRequest reviewSessionSummariesRequest) {
        Page page = pageRepository.findById(reviewSessionSummariesRequest.getPageId()).orElseThrow(
                ()->new IllegalStateException("page not found !")
        );
        ReviewSession reviewSession =  reviewSessionRepository.findById(reviewSessionSummariesRequest.getReviewSessionId())
                .orElseThrow( ()->new IllegalStateException("ReviewSession not found !") );
        ReviewSessionSummaries reviewSessionSummaries = ReviewSessionSummaries.builder()
                .difficulty(reviewSessionSummariesRequest.getDifficulty())
                .page(page)
                .reviewSession(reviewSession)
                .build();
        reviewSessionSummariesRepository.save(reviewSessionSummaries);
        return reviewSessionSummaries;
    }


    public ReviewSessionSummaries update(int pageId, int difficulty) {
        ReviewSessionSummaries reviewSessionSummaries =
                reviewSessionSummariesRepository.getReviewSessionSummariesByPageId(pageId);
        reviewSessionSummaries.setDifficulty(difficulty);
        reviewSessionSummariesRepository.save(reviewSessionSummaries);
        return reviewSessionSummaries;

    }



}

package com.flowna.app.ReviewSession.v1;

import com.flowna.app.lecture.v1.Lecture;
import com.flowna.app.lecture.v1.LectureRepository;
import com.flowna.app.user.User;
import com.flowna.app.user.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
public class ReviewSessionService {
    final
    UserRepository userRepository;
    final
    LectureRepository lectureRepository;

    public ReviewSessionService(UserRepository userRepository, LectureRepository lectureRepository) {
        this.userRepository = userRepository;
        this.lectureRepository = lectureRepository;
    }

    public ReviewSession create(@AuthenticationPrincipal User userDetails,
                                ReviewSessionRequest reviewSessionRequest) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow(
                ()-> new IllegalStateException("User not found!")
        );
        Lecture lecture = lectureRepository.findById(reviewSessionRequest.getLectureId()).orElseThrow(
                ()-> new IllegalStateException("Lecture not found!")
        );

        ReviewSession reviewSession = ReviewSession.builder()
                .title(reviewSessionRequest.getTitle())
                .typ(reviewSessionRequest.getTyp())
                .nextReview(reviewSessionRequest.getNextReview())
                .completed(reviewSessionRequest.isCompleted())
                .history(reviewSessionRequest.getHistory())
                .round(reviewSessionRequest.getRound())
                .maxRounds(reviewSessionRequest.getMaxRounds())
                .approved(reviewSessionRequest.isApproved())
                .AVG(reviewSessionRequest.getAVG())
                .user(user)
                .lecture(lecture)
                .build();


        return reviewSession;
    }









}

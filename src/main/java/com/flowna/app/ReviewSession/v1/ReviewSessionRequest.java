package com.flowna.app.ReviewSession.v1;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSessionRequest {
    private int id;
    private String title;
    private int lectureId;
    private String typ;
    private LocalDateTime nextReview;
    private boolean completed;
    private String history;
    private int round;
    private int maxRounds;
    private boolean approved;
    private float AVG;


}

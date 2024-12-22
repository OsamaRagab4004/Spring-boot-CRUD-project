package com.flowna.app.ReviewSession.v1.ReviewSessionSummaries;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSessionSummariesRequest {
    private int id;
    private int pageId;
    private int reviewSessionId;
    private int difficulty;
}

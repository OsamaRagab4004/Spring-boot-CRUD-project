package com.flowna.app.Tracking;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingRequest {
    private int id;
    private int deckId;
    private int courseId;
    private int lectureId;
    private int[] tagIds;
    private String typ;
    private LocalDateTime completedAt;

}

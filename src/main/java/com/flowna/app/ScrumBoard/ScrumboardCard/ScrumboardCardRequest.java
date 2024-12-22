package com.flowna.app.ScrumBoard.ScrumboardCard;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrumboardCardRequest {
    private int id;
    private int boardId;
    private int listId;
    private String title;
    private String description;
    private int dueDate;
    private boolean completed;
    private int priority;
    private LocalDateTime completedAt;




}

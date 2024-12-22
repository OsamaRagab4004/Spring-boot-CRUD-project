package com.flowna.app.ScrumBoard.ScrumboardList;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrumboardListRequest {
    private int id;
    private int boardId;
    private String title;
    private int position;
}

package com.flowna.app.ScrumBoard.board;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrumboardRequest {
    private int id;
    private String title;
    private String description;
    private String lastActivity;
    private String settings;
}

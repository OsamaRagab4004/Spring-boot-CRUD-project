package com.flowna.app.ScrumBoard.ScrumboardLabel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrumboardLabelRequest {
    private int id;
    private int scrumboardId;
    private int cardId;
    private String name;







}

package com.flowna.app.FlashcardsGroup.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashcardsGroupRequest {

    private int id;
    private int deckId;
    private int courseId;
    private int lectureId;
    private String title;
    private String tableContent;
}

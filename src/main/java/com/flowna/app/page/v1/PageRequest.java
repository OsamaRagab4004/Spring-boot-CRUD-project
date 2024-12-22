package com.flowna.app.page.v1;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
    private int id;
    private int deckId;
    private int courseId;
    private int lectureId;
    private String title;
    private String type;
    private String jsonText;
    private String youtubeUrl;
    private String mindmapText;


}

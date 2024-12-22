package com.flowna.app.LectureContent.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureContentRequest {
    private int id;
    private int lectureId;
    private int startPage;
    private int endPage;
    private String content;
    private String tableOfContent;
}

package com.flowna.app.lecture.v1;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureRequest {
    private int id;
    private String name;
    private int courseId;
    private int deckId;
}

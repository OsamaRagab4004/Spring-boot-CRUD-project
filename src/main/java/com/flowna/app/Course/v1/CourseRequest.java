package com.flowna.app.Course.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    private int id;
    private String name;
    private int deckId;
    private String deckName;

}

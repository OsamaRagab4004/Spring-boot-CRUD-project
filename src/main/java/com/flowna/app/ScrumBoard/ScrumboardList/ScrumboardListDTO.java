package com.flowna.app.ScrumBoard.ScrumboardList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrumboardListDTO {
    private Integer id;
    private List<String> cards;

    // Constructors, getters, and setters
}

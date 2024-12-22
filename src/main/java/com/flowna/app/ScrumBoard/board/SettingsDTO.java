package com.flowna.app.ScrumBoard.board;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingsDTO {
    private boolean subscribed;
    private boolean cardCoverImages;

    // Constructors, getters, and setters
}

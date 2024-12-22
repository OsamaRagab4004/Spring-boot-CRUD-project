package com.flowna.app.deck.v1;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeckRequest {

    @Size(min = 3, message = "Name must be at least 3 characters long")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_\\-]*$", message = "Name must start with a letter and contain only letters, numbers, dashes, or underscores")
    private String name;
    private int id;
    private int courseId;
}

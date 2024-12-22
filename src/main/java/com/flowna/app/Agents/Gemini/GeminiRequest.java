package com.flowna.app.Agents.Gemini;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeminiRequest {
    private String model_typ;
    private String user_prompt;
    private String model_prompt;
}

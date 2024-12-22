package com.flowna.app.Agents.Gemini;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeminiResponse {
    private String message;
    private int promptTokens;
    private int responseTokens;
    private int totalTokens;
    private String modelVersion;
    private double avgLogprobs;
}
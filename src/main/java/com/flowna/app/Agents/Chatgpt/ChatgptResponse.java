package com.flowna.app.Agents.Chatgpt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatgptResponse {
    private String message;
    private int prompt_tokens;
    private int response_tokens;
    private int total_tokens;

}

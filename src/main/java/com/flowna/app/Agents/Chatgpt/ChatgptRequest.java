package com.flowna.app.Agents.Chatgpt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatgptRequest {
    private String model_typ;
    private String user_prompt;
    private String model_prompt;
}

package com.flowna.app.Chat.Message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private int id;
    private int chatId;
    private String message;
    private boolean aiMsg;
    private String modelTyp;
    private String modelPrompt;

}

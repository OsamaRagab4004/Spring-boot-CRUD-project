package com.flowna.app.Agents.Chatgpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/chatgpt")
public class ChatgptController {

    @Autowired
    private ChatgptService chatgptService;

    @PostMapping("/response")
    public ResponseEntity<ChatgptResponse> getChatGPTResponse(@RequestBody ChatgptRequest chatgptRequest) {
        try {
            ChatgptResponse response = chatgptService.getChatGPTResponse(chatgptRequest).get();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
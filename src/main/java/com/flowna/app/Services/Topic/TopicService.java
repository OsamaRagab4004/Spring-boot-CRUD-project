package com.flowna.app.Services.Topic;

import com.flowna.app.Agents.Chatgpt.ChatGPT4;
import com.flowna.app.Agents.Chatgpt.ChatgptRequest;
import com.flowna.app.Agents.Chatgpt.ChatgptService;
import com.flowna.app.Agents.Gemini.GeminiService;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TopicService {
    final ChatgptService chatgptService;
    final GeminiService geminiService;

    public TopicService(ChatgptService chatgptService, GeminiService geminiService) {
        this.chatgptService = chatgptService;
        this.geminiService = geminiService;
    }


    public CompletableFuture<TopicResponse> generateTopics(TopicRequest request) {
        ChatgptRequest chatgptRequest = ChatgptRequest.builder()
                .user_prompt()
                .model_prompt(ChatGPT4.ChatGPT_MINI)
                .model_typ()
                .build();


        return null;
    }

}

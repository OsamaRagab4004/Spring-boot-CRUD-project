package com.flowna.app.Agents.Chatgpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Service
public class ChatgptService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

   @Async
   public CompletableFuture<ChatgptResponse> getChatGPTResponse(ChatgptRequest ChatgptRequest) {

    String requestBodyJson = "{"
            + "\"model\": \"" +ChatgptRequest.getModel_typ()+ "\","
            + "\"messages\": ["
            + "{"
            + "\"role\": \"system\","
            + "\"content\": \""+escapeJson(ChatgptRequest.getModel_prompt())+"\""
            + "},"
            + "{"
            + "\"role\": \"user\","
            + "\"content\": \"" + escapeJson(ChatgptRequest.getUser_prompt()) + "\""
            + "}"
            + "]"
            + "}";

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/chat/completions"))
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
            .build();

    return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenCompose(response -> {
                if (response.statusCode() == 200) {
                    try {
                        JsonNode root = objectMapper.readTree(response.body());
                        JsonNode choices = root.path("choices");
                        JsonNode usage = root.path("usage");
                        if (choices.isArray() && choices.size() > 0) {
                            String content = choices.get(0).path("message").path("content").asText("");
                            int prompt_tokens = usage.path("prompt_tokens").asInt();
                            int response_tokens = usage.path("completion_tokens").asInt();
                            int total_tokens = usage.path("total_tokens").asInt();
                            ChatgptResponse chatgptResponse = ChatgptResponse.builder()
                                    .message(content)
                                    .prompt_tokens(prompt_tokens)
                                    .response_tokens(response_tokens)
                                    .total_tokens(total_tokens)
                                    .build();
                            //System.out.println("ChatgptResponse: "+chatgptResponse);
                            return CompletableFuture.completedFuture(chatgptResponse);
                        } else {
                        //    System.out.println("Array Error ");
                            return CompletableFuture.completedFuture(null);
                        }
                    } catch (IOException e) {
                        CompletableFuture<ChatgptResponse> errorFuture = new CompletableFuture<>();
                        errorFuture.completeExceptionally(e);
                      //  System.out.println("IOException Error "+errorFuture);
                        return errorFuture;
                    }
                } else {
                    CompletableFuture<ChatgptResponse> errorFuture = new CompletableFuture<>();
                    errorFuture.completeExceptionally(
                            new RuntimeException("Error from API: HTTP " + response.statusCode() + " - " + response.body())
                    );
                    //System.out.println("IOException Error "+errorFuture);
                    return errorFuture;
                }
            });
}

    // A simple helper to escape user input for JSON strings
    private String escapeJson(String text) {
        return text.replace("\"", "\\\"");
    }
}
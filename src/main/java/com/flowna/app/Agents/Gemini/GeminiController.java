package com.flowna.app.Agents.Gemini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/response")
    public ResponseEntity<GeminiResponse> generateContent(@RequestBody GeminiRequest geminiRequest) {
        try {
            GeminiResponse response = geminiService.getGeminiResponse(geminiRequest).get();
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }
}

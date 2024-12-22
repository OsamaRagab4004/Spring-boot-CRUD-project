package com.flowna.app.Chat.Message;

import com.flowna.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/chats/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/create")
    public ResponseEntity<Message> createMessage(
            @RequestBody MessageRequest messageRequest,
            @AuthenticationPrincipal User userDetails) {
        Message message = messageService.createMessage(messageRequest, userDetails.getId());
        return ResponseEntity.ok(message);
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessageToChatGPT(
            @RequestBody MessageRequest messageRequest,
            @AuthenticationPrincipal User userDetails) {
        try {
            Message message = messageService.sendMessageToChatGPT(messageRequest, userDetails.getId()).get();
            return ResponseEntity.ok(message);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/generate-next-questions")
    public ResponseEntity<String> generateNextQuestions(
            @RequestBody MessageRequest messageRequest,
            @AuthenticationPrincipal User userDetails) {
        try {
            String nextQuestions = messageService.generateNextQuestions(messageRequest, userDetails.getId()).get();
            return ResponseEntity.ok(nextQuestions);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/{chatId}")
    public ResponseEntity<List<Message>> getAllMessagesByChatId(
            @PathVariable int chatId,
            @AuthenticationPrincipal User userDetails) {
        List<Message> messages = messageService.getAllMessagesByChatId(chatId, userDetails.getId());
        return ResponseEntity.ok(messages);
    }
}
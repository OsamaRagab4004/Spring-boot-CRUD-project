package com.flowna.app.Chat;

import com.flowna.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/create")
    public ResponseEntity<Chat> createChat(
            @RequestBody ChatRequest chatRequest,
            @AuthenticationPrincipal User userDetails) {
        Chat chat = chatService.createChat(chatRequest, userDetails.getId());
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Chat>> getAllChats(
            @AuthenticationPrincipal User userDetails
    ) {
        Set<Chat> chats = chatService.getAllChats(userDetails.getId());
        return ResponseEntity.ok(chats);
    }

    @PutMapping("/update")
        public ResponseEntity<Chat> updateChat(
                @RequestBody ChatRequest chatRequest,
                @AuthenticationPrincipal User userDetails) {
                Chat updatedChat = chatService.updateChat(chatRequest, userDetails.getId());
                return ResponseEntity.ok(updatedChat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable int id,
                                           @AuthenticationPrincipal User userDetails) {
        chatService.deleteChat(id, userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
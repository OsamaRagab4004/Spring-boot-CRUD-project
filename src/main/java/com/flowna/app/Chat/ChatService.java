package com.flowna.app.Chat;
import com.flowna.app.user.User;
import com.flowna.app.user.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }





    @Transactional
 public Chat createChat(ChatRequest chatRequest, int userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    Chat chat = Chat.builder()
            .title(chatRequest.getTitle())
            .users(new HashSet<>())
            .build();
        chat.getUsers().add(user);
        user.getChats().add(chat);

        // Save the chat first
        Chat savedChat = chatRepository.save(chat);

        // Since the relationship is bidirectional and both sides are set,
        // saving either chat or user should persist the relationship.
        // Optionally, you can save the user again if needed:
        userRepository.save(user);

        return savedChat;
}
    public Set<Chat> getAllChats(int userId) {
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return chatRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

    @PreAuthorize("@CheckOwner.isChatOwnedByUser(#chatRequest.id, #userId)")
    @Transactional
    public Chat updateChat(ChatRequest chatRequest, int userId) {
        Chat chat = chatRepository.findById(chatRequest.getId()).orElseThrow(() -> new RuntimeException("Chat not found"));
        chat.setTitle(chatRequest.getTitle());
        return chatRepository.save(chat);
    }

    @PreAuthorize("@CheckOwner.isChatOwnedByUser(#chatRequest.id, #userId)")
    @Transactional
   public void deleteChat(int id, int userId) {
    Chat chat = chatRepository.findById(id).orElseThrow(() -> new RuntimeException("Chat not found"));
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    user.getChats().remove(chat);
    userRepository.save(user);
    chatRepository.delete(chat);
}



}

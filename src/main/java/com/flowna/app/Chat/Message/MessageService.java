package com.flowna.app.Chat.Message;

import com.flowna.app.Agents.Chatgpt.ChatgptRequest;
import com.flowna.app.Agents.Chatgpt.ChatgptResponse;
import com.flowna.app.Agents.Chatgpt.ChatgptService;
import com.flowna.app.Chat.Chat;
import com.flowna.app.Chat.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final ChatgptService chatgptService;

    @Autowired
    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository, ChatgptService chatgptService) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.chatgptService = chatgptService;
    }

    // Create a new message

    @PreAuthorize("@CheckOwner.isChatOwnedByUser(#messageRequest.chatId,#userId)")
    @Transactional
    public Message createMessage(MessageRequest messageRequest, int userId) {
        Chat chat = chatRepository.findById(messageRequest.getChatId()).orElseThrow(() -> new NoSuchElementException("Chat not found"));
        Message message = Message.builder()
                .message(messageRequest.getMessage())
                .aiMsg(messageRequest.isAiMsg())
                .chat(chat)
                .build();
        return messageRepository.save(message);
    }



    @PreAuthorize("@CheckOwner.isChatOwnedByUser(#messageRequest.chatId,#userId)")
    @Transactional
    public CompletableFuture<Message> sendMessageToChatGPT(MessageRequest messageRequest, int userId) {
        Chat chat = chatRepository.findById(messageRequest.getChatId()).orElseThrow(() -> new NoSuchElementException("Chat not found"));
        Message message = Message.builder()
                .message(messageRequest.getMessage())
                .aiMsg(false)
                .chat(chat)
                .build();
        Message userMsg = messageRepository.save(message);
        System.out.println("User message saved");

        ChatgptRequest chatgptRequest = ChatgptRequest.builder()
                .model_typ(messageRequest.getModelTyp())
                .model_prompt(messageRequest.getModelPrompt())
                .user_prompt(messageRequest.getMessage())
                .build();
        System.out.println("request created" + chatgptRequest);
        System.out.println("ChatGPT request created" + chatgptRequest);
        CompletableFuture<ChatgptResponse> chatGPTResponse = chatgptService.getChatGPTResponse(chatgptRequest);
        System.out.println("ChatGPT response received" + chatGPTResponse);
        return chatGPTResponse.thenCompose(response -> {
            Message responseMsg = Message.builder()
                    .message(response.getMessage())
                    .aiMsg(true)
                    .chat(chat)
                    .build();
            Message chatGptMsg = messageRepository.save(responseMsg);
            return CompletableFuture.completedFuture(chatGptMsg);
        });
    }


    @PreAuthorize("@CheckOwner.isChatOwnedByUser(#messageRequest.chatId,#userId)")
    public CompletableFuture<String> generateNextQuestions(MessageRequest messageRequest, int userId) {
        ChatgptRequest chatgptRequest = ChatgptRequest.builder()
                .model_typ(messageRequest.getModelTyp())
                .model_prompt(messageRequest.getModelPrompt())
                .user_prompt(messageRequest.getMessage())
                .build();
        return chatgptService.getChatGPTResponse(chatgptRequest).thenApply(ChatgptResponse::getMessage);
    }


    @PreAuthorize("@CheckOwner.isChatOwnedByUser(#chatId,#userId)")
    public List<Message> getAllMessagesByChatId(int chatId, int userId) {
        List<Message> messages = messageRepository.findAllByChatIdOrderBysentAtDesc(chatId);
        if (messages.isEmpty()) {
            throw new NoSuchElementException("Chat not found");
        }
        return messages;
    }


}
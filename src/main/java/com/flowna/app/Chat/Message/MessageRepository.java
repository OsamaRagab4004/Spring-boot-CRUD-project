package com.flowna.app.Chat.Message;

import com.flowna.app.Chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    @Query("SELECT msg FROM Message msg JOIN msg.chat c WHERE c.id = :chatId ORDER BY msg.sentAt DESC")
    List<Message> findAllByChatIdOrderBysentAtDesc(@Param("chatId") int chatId);
}

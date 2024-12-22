package com.flowna.app.Chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id = :userId ORDER BY c.createdAt DESC")
    Set<Chat> findAllByUserIdOrderByCreatedAtDesc(@Param("userId") int userId);
}
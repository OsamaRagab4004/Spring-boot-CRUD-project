package com.flowna.app.Chat.Message;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowna.app.Chat.Chat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10485760) // Example size, adjust as needed
    private String message;

    @Column
    private LocalDateTime sentAt;
    @Column
    private boolean aiMsg;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @PrePersist
    public void prePersist() {
        this.sentAt = LocalDateTime.now();
    }





}

package com.flowna.app.Chat;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowna.app.Chat.Message.Message;
import com.flowna.app.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"users"})
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private LocalDateTime createdAt;
    @Column
    private String title;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_chat",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private List<Message> messages;


}

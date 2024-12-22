package com.flowna.app.Report.v1;

import com.flowna.app.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String contentJson;
    @Column
    private String badge;
    @CreationTimestamp
    @Column
    LocalDateTime createdAt;
    @Column
    private LocalDateTime start;
    @Column(name = "\"end\"")
    private LocalDateTime end;
    @Column
    private float totalHours;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }





}

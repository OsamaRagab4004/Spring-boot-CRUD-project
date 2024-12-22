package com.flowna.app.ReviewSession.v1;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowna.app.ReviewSession.v1.ReviewSessionFlashcards.ReviewSessionFlashcards;
import com.flowna.app.ReviewSession.v1.ReviewSessionSummaries.ReviewSessionSummaries;
import com.flowna.app.lecture.v1.Lecture;
import com.flowna.app.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review_session")
public class ReviewSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private String typ;
    @Column
    LocalDateTime nextReview;
    @Column
    private boolean completed;
    @Column
    private String history;
    @Column
    private int round;
    @Column
    private int maxRounds;
    @Column
    private boolean approved;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column
    private float AVG;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    User user;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lecture_id")
    Lecture lecture;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reviewSession")
    Set<ReviewSessionFlashcards> reviewSessionFlashcards;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reviewSession")
    Set<ReviewSessionSummaries> reviewSessionSummaries = new HashSet<>();


    @PrePersist
    public void setup() {
        this.approved = false;
        this.completed= false;
        this.createdAt = LocalDateTime.now();
        this.AVG = 0f;
    }








}

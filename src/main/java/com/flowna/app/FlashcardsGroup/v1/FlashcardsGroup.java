package com.flowna.app.FlashcardsGroup.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowna.app.Flashcard.v1.Flashcard;
import com.flowna.app.ReviewSession.v1.ReviewSessionFlashcards.ReviewSessionFlashcards;
import com.flowna.app.lecture.v1.Lecture;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flashcards_group")
public class FlashcardsGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private String tableContent;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "flashcardsGroup")
    private Set<Flashcard> flashcards;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "flashcardsGroup")
    private ReviewSessionFlashcards reviewSessionFlashcards;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }




}

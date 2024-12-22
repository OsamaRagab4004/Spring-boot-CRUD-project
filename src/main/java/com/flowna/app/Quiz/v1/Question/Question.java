package com.flowna.app.Quiz.v1.Question;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowna.app.Quiz.v1.Choices.Choice;
import com.flowna.app.Quiz.v1.Quiz.Quiz;
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
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String question;
    @Column
    private String type;
    @Column
    private String answer;
    @Column
    private String badge;
    @Column
    private int correctTimes;
    @Column
    private int wrongTimes;
    @Column
    private int aiPredictionDifficulty;
    @Column
    private String pdfReference;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="quiz_id")
    Quiz quiz;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "question")
    Set<Choice> choices = new HashSet<>();



    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.wrongTimes=0;
        this.correctTimes=0;
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}

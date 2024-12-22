package com.flowna.app.Quiz.v1.Choices;


import com.flowna.app.Quiz.v1.Question.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "choice")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String content;
    @Column(nullable = false)
    private boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    Question question;



}

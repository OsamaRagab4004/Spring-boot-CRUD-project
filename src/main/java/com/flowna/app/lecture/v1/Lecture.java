package com.flowna.app.lecture.v1;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowna.app.Course.v1.Course;
import com.flowna.app.FlashcardsGroup.v1.FlashcardsGroup;
import com.flowna.app.LectureContent.v1.LectureContent;
import com.flowna.app.Quiz.v1.Quiz.Quiz;
import com.flowna.app.ReviewSession.v1.ReviewSession;
import com.flowna.app.Tracking.Tracking;
import com.flowna.app.page.v1.Page;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "lecture")
    Set<LectureContent> lectureContents = new HashSet<>();


    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "lecture")
    Set<Page> pages = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "lecture")
    Set<FlashcardsGroup> flashcardsGroups;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "lecture")
    Set<Quiz> quizzes;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "lecture")
    ReviewSession reviewSession;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "lecture")
    Tracking tracking;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}

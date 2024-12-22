package com.flowna.app.Course.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowna.app.Tracking.Tracking;
import com.flowna.app.deck.v1.Deck;
import com.flowna.app.lecture.v1.Lecture;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"decks"})
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToMany(mappedBy = "courses")
    @Builder.Default
    private Set<Deck> decks = new HashSet<>();

    // Delete Course, will delete all related lectures, even for shared courses.
    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Lecture> lectures = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "course", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
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

package com.flowna.app.Tracking;


import com.flowna.app.Course.v1.Course;
import com.flowna.app.Tracking.tag.Tag;
import com.flowna.app.lecture.v1.Lecture;
import com.flowna.app.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tracking")
public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String typ;


    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;
    @CreationTimestamp
    @Column(nullable = false, name = "completed_at")
    private LocalDateTime completedAt;

    @Column(nullable = false)
    private boolean completed;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    Course course;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    Lecture lecture;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tracking_tag",
            joinColumns = @JoinColumn(name = "tracking_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    Set<Tag> tags = new HashSet<>();






    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.completedAt = LocalDateTime.now();
        this.completed= false;
    }





}

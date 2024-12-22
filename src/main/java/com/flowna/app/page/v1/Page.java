package com.flowna.app.page.v1;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowna.app.ReviewSession.v1.ReviewSessionSummaries.ReviewSessionSummaries;
import com.flowna.app.lecture.v1.Lecture;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "page")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private String type;
    @Column
    private String pageType;
    @Column
    private String jsonText;
    @Column
    private String youtubeUrl;
    @Column
    private String mindmapText;
    @CreationTimestamp
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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "page")
    ReviewSessionSummaries reviewSessionSummaries;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}

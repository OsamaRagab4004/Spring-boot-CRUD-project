package com.flowna.app.LectureContent.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flowna.app.lecture.v1.Lecture;
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
@Table(name = "lecture_content")
public class LectureContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private int startPage;
    @Column(nullable = false)
    private int endPage;
    @Column
    private String content;
    @Column
    private String tableOfContent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;



}

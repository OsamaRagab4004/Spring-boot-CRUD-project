package com.flowna.app.ReviewSession.v1.ReviewSessionSummaries;


import com.flowna.app.ReviewSession.v1.ReviewSession;
import com.flowna.app.page.v1.Page;
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
@Table(name = "review_session_summuries")
public class ReviewSessionSummaries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private int difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_session_id")
    ReviewSession reviewSession;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="page_id")
    Page page;

}

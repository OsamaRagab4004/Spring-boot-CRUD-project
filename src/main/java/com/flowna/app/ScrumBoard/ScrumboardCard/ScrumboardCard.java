package com.flowna.app.ScrumBoard.ScrumboardCard;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.flowna.app.ScrumBoard.ScrumboardCardChecklist.ScrumboardCardChecklist;
import com.flowna.app.ScrumBoard.ScrumboardLabel.ScrumboardLabel;
import com.flowna.app.ScrumBoard.ScrumboardList.ScrumboardList;
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
@Table(name = "scrumboard_card")
public class ScrumboardCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private int dueDate;
    @Column
    private  boolean completed;
    @Column
    private int priority;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;


    @Column
    private LocalDateTime completedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrumboard_list_id")
    @JsonBackReference
    ScrumboardList scrumboardList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "scrumboard_card_labels",
            joinColumns = @JoinColumn(name = "scrumboard_card_id"),
            inverseJoinColumns = @JoinColumn(name = "scrumboard_label_id")
    )
    Set<ScrumboardLabel> scrumboardLabels = new HashSet<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "scrumboardCard")
    Set<ScrumboardCardChecklist> scrumboardCardChecklists = new HashSet<>();

    @PrePersist
    protected void onCreate() {

        this.createdAt = LocalDateTime.now();
        this.completed = false;
        this.completedAt = null;
    }








}

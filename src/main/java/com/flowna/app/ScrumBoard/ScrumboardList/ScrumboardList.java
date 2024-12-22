package com.flowna.app.ScrumBoard.ScrumboardList;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flowna.app.ScrumBoard.ScrumboardCard.ScrumboardCard;
import com.flowna.app.ScrumBoard.board.Scrumboard;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"scrumboardCards"})

@Table(name = "scrumboard_list")
public class ScrumboardList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;

    @Column
    private int position; // Field to store the order


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrumboard_id")
    @JsonBackReference
    Scrumboard scrumboard;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true,mappedBy = "scrumboardList")
    @JsonManagedReference
    Set<ScrumboardCard> scrumboardCards = new HashSet<>();



}

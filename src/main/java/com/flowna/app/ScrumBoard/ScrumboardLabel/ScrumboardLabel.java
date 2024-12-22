package com.flowna.app.ScrumBoard.ScrumboardLabel;


import com.flowna.app.ScrumBoard.ScrumboardCard.ScrumboardCard;
import com.flowna.app.ScrumBoard.board.Scrumboard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "scrumboard_label")
public class ScrumboardLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "scrumboard_card_labels",
            joinColumns = @JoinColumn(name = "scrumboard_label_id"),
            inverseJoinColumns = @JoinColumn(name = "scrumboard_card_id")
    )
    Set<ScrumboardCard> scrumboardCards = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrumboard_id")
    Scrumboard scrumboard;



}

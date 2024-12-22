package com.flowna.app.ScrumBoard.ScrumboardCardChecklist;


import com.flowna.app.ScrumBoard.CardChecklistItems.CardChecklistItems;
import com.flowna.app.ScrumBoard.ScrumboardCard.ScrumboardCard;
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
@Table(name = "scrumboard_card_checklist")
public class ScrumboardCardChecklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scrumboard_card_id")
    ScrumboardCard scrumboardCard;

    @OneToMany(mappedBy = "scrumboardCardChecklist", fetch = FetchType.EAGER)
    private Set<CardChecklistItems> cardChecklistItems = new HashSet<>();



}

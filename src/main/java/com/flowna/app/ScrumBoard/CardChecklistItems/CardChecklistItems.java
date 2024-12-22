package com.flowna.app.ScrumBoard.CardChecklistItems;

import com.flowna.app.ScrumBoard.ScrumboardCardChecklist.ScrumboardCardChecklist;
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
@Table(name = "card_checklist_items")
public class CardChecklistItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Boolean checked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrumboard_card_checklist_id")
    ScrumboardCardChecklist scrumboardCardChecklist;

}

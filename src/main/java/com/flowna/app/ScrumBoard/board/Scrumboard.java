package com.flowna.app.ScrumBoard.board;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flowna.app.ScrumBoard.ScrumboardLabel.ScrumboardLabel;
import com.flowna.app.ScrumBoard.ScrumboardList.ScrumboardList;
import com.flowna.app.ScrumBoard.member.Member;
import com.flowna.app.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "scrumboard")
@EqualsAndHashCode(exclude = {"userSet", "members", "scrumboardLists", "scrumboardLabels"})
public class Scrumboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private String description;
    @Column(nullable = true)
    private String icon;
    @Column
    private String lastActivity;
    @Column
    private String settings;



    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "scrumboards")
    Set<User> userSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scrumboard")
    Set<Member> members = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scrumboard")
    @JsonManagedReference
    Set<ScrumboardList> scrumboardLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scrumboard")
    Set<ScrumboardLabel> scrumboardLabels = new HashSet<>();


}

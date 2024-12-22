package com.flowna.app.ScrumBoard.member;


import com.flowna.app.ScrumBoard.board.Scrumboard;
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
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String avatar;





    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    Scrumboard scrumboard;


}

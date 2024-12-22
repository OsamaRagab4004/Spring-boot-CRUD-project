package com.flowna.app.Calender.Label;


import com.flowna.app.Calender.Calender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "calender_label")
public class CalenderLaberl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private String color;

    @OneToMany(mappedBy = "calenderLaberl", fetch = FetchType.LAZY)
    private List<Calender> calenders;

}
